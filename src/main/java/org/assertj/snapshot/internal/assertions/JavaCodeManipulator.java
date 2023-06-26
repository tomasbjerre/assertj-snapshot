package org.assertj.snapshot.internal.assertions;

import com.github.javaparser.ParserConfiguration.LanguageLevel;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.util.List;
import java.util.Optional;
import org.assertj.snapshot.internal.utils.TestCaseFinder.AssertingTestCase;

public class JavaCodeManipulator {

  public static String manipulateTestCase(
      final AssertingTestCase testCase,
      final String testCaseContent,
      final String jsonToUseAsExpected) {
    StaticJavaParser.getParserConfiguration().setLanguageLevel(LanguageLevel.JAVA_17);
    final CompilationUnit compilationUnit = StaticJavaParser.parse(testCaseContent);

    final String classSimpleName =
        testCase.getClassName().substring(testCase.getClassName().lastIndexOf(".") + 1);
    final Optional<ClassOrInterfaceDeclaration> classOrINterfaceOpt =
        compilationUnit.getClassByName(classSimpleName);

    if (classOrINterfaceOpt.isEmpty()) {
      throw new IllegalStateException("Cannot find " + classSimpleName + " in " + testCaseContent);
    }
    final ClassOrInterfaceDeclaration classOrINterface = classOrINterfaceOpt.get();

    final List<MethodDeclaration> methods =
        classOrINterface.getMethodsByName(testCase.getMethodName());
    if (methods.isEmpty() || methods.size() > 1) {
      throw new IllegalStateException(
          "Cannot find " + testCase.getMethodName() + " in " + testCaseContent);
    }
    final MethodDeclaration method = methods.get(0);

    final BlockStmt methodBlock = method.getBody().get();

    for (final Statement statement : methodBlock.getStatements()) {
      final List<MethodCallExpr> methodCallExprs = statement.findAll(MethodCallExpr.class);
      for (final MethodCallExpr methodCallExpr : methodCallExprs) {
        final List<SimpleName> allSimpleNameNodes = methodCallExpr.findAll(SimpleName.class);

        final boolean ifMatchesInlineSnapshot =
            allSimpleNameNodes.stream()
                .filter(
                    it -> {
                      return it.getId().equals("matchesInlineSnapshot")
                          && it.getBegin().get().line == testCase.getLineNumber();
                    })
                .findFirst()
                .isPresent();

        if (ifMatchesInlineSnapshot) {
          if (methodCallExpr.getArguments().size() == 0) {
            methodCallExpr.addArgument(new TextBlockLiteralExpr(jsonToUseAsExpected));
          } else if (methodCallExpr.getArguments().size() == 1) {
            methodCallExpr.setArgument(0, new TextBlockLiteralExpr(jsonToUseAsExpected));
          } else {
            throw new IllegalStateException("Cannot find argument");
          }
          return compilationUnit.toString();
        }
      }
    }

    throw new IllegalStateException(
        "Unable to match assertion\n" + testCaseContent + "\n\n" + testCaseContent);
  }
}
