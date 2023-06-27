package org.assertj.snapshot.internal.assertions;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.assertj.snapshot.api.UpdateMode;
import org.assertj.snapshot.internal.utils.FileUtils;
import org.assertj.snapshot.internal.utils.FileUtilsImpl;
import org.assertj.snapshot.internal.utils.JSONUtils;
import org.assertj.snapshot.internal.utils.SourceCodeLocator;
import org.assertj.snapshot.internal.utils.TestCaseFinder;
import org.assertj.snapshot.internal.utils.TestCaseFinder.AssertingTestCase;

class InternalInlineSnapshotCapturer {
  static final String CAPTURE_SNAPSHOT = "<capture-snapshot>";

  public static void assertEqual(
      final Object actual, final String expected, final UpdateMode updateMode) {

    final boolean expectedDoesNotExist =
        expected == null || expected.equals(CAPTURE_SNAPSHOT) || expected.trim().isEmpty();
    final boolean shouldUpdate =
        expectedDoesNotExist && updateMode == UpdateMode.UPDATE_IF_NO_PREVIOUS_SNAPSHOT //
            || updateMode == UpdateMode.UPDATE_ALWAYS;
    if (shouldUpdate) {
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

    final String jsonToUseAsExpected = JSONUtils.prettyPrint(actual) + "\n";

    final String manipulatedTestCaseContent =
        JavaCodeManipulator.manipulateTestCase(
            testCase, testCaseSourceFileContent, jsonToUseAsExpected);

    fileUtils.writeFileContent(testCaseSourceFile, manipulatedTestCaseContent);
  }

  private static Path getTestCaseSourceFile(final AssertingTestCase testCase) {
    final File sourceFolderOfTestCase =
        SourceCodeLocator.getSourceFolder(testCase.getClassName(), testCase.getFile());
    final Path testCaseSourceFile =
        Paths.get(sourceFolderOfTestCase.getAbsolutePath(), testCase.getFile());
    return testCaseSourceFile;
  }
}
