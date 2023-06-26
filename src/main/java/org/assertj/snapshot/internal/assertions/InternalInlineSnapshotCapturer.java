/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2022-2022 the original author or authors.
 */
package org.assertj.snapshot.internal.assertions;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.assertj.snapshot.internal.utils.FileUtils;
import org.assertj.snapshot.internal.utils.FileUtilsImpl;
import org.assertj.snapshot.internal.utils.JSONUtils;
import org.assertj.snapshot.internal.utils.SourceCodeLocator;
import org.assertj.snapshot.internal.utils.TestCaseFinder;
import org.assertj.snapshot.internal.utils.TestCaseFinder.AssertingTestCase;

/**
 * TODO: When any proper java editing library has support for java 17, use that instead of regexp.
 *
 * <p>https://github.com/javaparser/javaparser
 *
 * <p>https://spoon.gforge.inria.fr/
 */
class InternalInlineSnapshotCapturer {
  static final String CAPTURE_SNAPSHOT = "<capture-snapshot>";

  public static void assertEqual(final Object actual, final String expected) {
    if (expected == null || expected.equals(CAPTURE_SNAPSHOT) || expected.trim().isEmpty()) {
      InternalInlineSnapshotCapturer.captureInlineSnapshot(actual);
    } else {
      InternalAssertions.assertEqual(actual, expected);
    }
  }

  static void captureInlineSnapshot(final Object actual) {
    final FileUtils fileUtils = FileUtilsImpl.create();
    final AssertingTestCase testCase = TestCaseFinder.getTestCase();

    final Path testCaseSourceFile = getTestCaseSourceFile(testCase);
    final String testCaseSourceFileContent = fileUtils.getFileContent(testCaseSourceFile);

    final String jsonToUseAsExpected = JSONUtils.prettyPrint(actual);

    final String manipulatedTestCaseContent =
        manipulateTestCase(testCase, testCaseSourceFileContent, jsonToUseAsExpected);

    fileUtils.writeFileContent(testCaseSourceFile, manipulatedTestCaseContent);
  }

  private static Path getTestCaseSourceFile(final AssertingTestCase testCase) {
    final File sourceFolderOfTestCase =
        SourceCodeLocator.getSourceFolder(testCase.getClassName(), testCase.getFile());
    final Path testCaseSourceFile =
        Paths.get(sourceFolderOfTestCase.getAbsolutePath(), testCase.getFile());
    return testCaseSourceFile;
  }

  private static String manipulateTestCase(
      final AssertingTestCase testCase,
      final String testCaseContent,
      final String jsonToUseAsExpected) {
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
        final boolean ifMatchesAssertThat =
            allSimpleNameNodes.stream()
                .filter(it -> it.getId().equals("assertThat"))
                .findFirst()
                .isPresent();
        final boolean ifMatchesInlineSnapshot =
            allSimpleNameNodes.stream()
                .filter(it -> it.getId().equals("matchesInlineSnapshot"))
                .findFirst()
                .isPresent();
        if (ifMatchesAssertThat && ifMatchesInlineSnapshot) {
          if (methodCallExpr.getArguments().size() == 0) {
            methodCallExpr.addArgument(new TextBlockLiteralExpr(jsonToUseAsExpected));
          } else if (methodCallExpr.getArguments().size() == 1) {
            methodCallExpr.setArgument(0, new TextBlockLiteralExpr(jsonToUseAsExpected));
          } else {
            throw new IllegalStateException("Cannot find argument");
          }
        }
      }
    }

    return compilationUnit.toString();
  }
}
