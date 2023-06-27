package org.assertj.snapshot.internal.assertions;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.snapshot.api.UpdateMode;
import org.assertj.snapshot.internal.utils.FileUtils;
import org.assertj.snapshot.internal.utils.FileUtilsImpl;
import org.assertj.snapshot.internal.utils.JSONUtils;
import org.assertj.snapshot.internal.utils.SourceCodeLocator;
import org.assertj.snapshot.internal.utils.TestCaseFinder;
import org.assertj.snapshot.internal.utils.TestCaseFinder.AssertingTestCase;

public class InternalFileAssertions {

  private static final String SNAPSHOTS_DIR = "snapshots";

  public static void assertEqual(final Object actual, final UpdateMode updateMode) {
    final AssertingTestCase testCase = TestCaseFinder.getTestCase();
    final File sourceFolderOfTestCase =
        SourceCodeLocator.getSourceFolder(testCase.getClassName(), testCase.getFile());
    final Path snapshotsFolder = sourceFolderOfTestCase.toPath().resolve(SNAPSHOTS_DIR);
    final FileUtils fileUtils = FileUtilsImpl.create();
    if (!fileUtils.pathExists(snapshotsFolder)) {
      fileUtils.createDirs(snapshotsFolder);
    }
    final Path snapshotFile =
        snapshotsFolder.resolve(
            testCase.getClassName() + "_" + testCase.getMethodName() + ".snapshot.json");
    final Optional<String> snapshotOpt = fileUtils.findFileContent(snapshotFile);
    final String actualJson = JSONUtils.prettyPrint(actual);

    final boolean expectedDoesNotExist = !snapshotOpt.isPresent();
    final boolean shouldUpdate =
        expectedDoesNotExist && updateMode == UpdateMode.UPDATE_IF_NO_PREVIOUS_SNAPSHOT //
            || updateMode == UpdateMode.UPDATE_ALWAYS;
    if (shouldUpdate) {
      fileUtils.writeFileContent(snapshotFile, actualJson);
    } else {
      final String normalized = JSONUtils.prettyPrint(snapshotOpt.get());
      Assertions.assertThat(actualJson).isEqualTo(normalized);
    }
  }
}
