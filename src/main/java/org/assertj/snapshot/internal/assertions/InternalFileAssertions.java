package org.assertj.snapshot.internal.assertions;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.snapshot.internal.utils.FileUtils;
import org.assertj.snapshot.internal.utils.JSONUtils;
import org.assertj.snapshot.internal.utils.SourceCodeLocator;
import org.assertj.snapshot.internal.utils.TestCaseFinder;
import org.assertj.snapshot.internal.utils.TestCaseFinder.AssertingTestCase;

public class InternalFileAssertions {

  private static final String SNAPSHOTS_DIR = "snapshots";

	public static void assertEqual(final Object actual) {
		    final AssertingTestCase testCase = TestCaseFinder.getTestCase();
		    final File sourceFolderOfTestCase =
		        SourceCodeLocator.getSourceFolder(testCase.getClassName(), testCase.getFile());
			final Path snapshotsFolder = sourceFolderOfTestCase.toPath().resolve(SNAPSHOTS_DIR);
			FileUtils.createDirs(snapshotsFolder);
			final Path snapshotFile = snapshotsFolder.resolve(testCase.getClassName()+"_"+testCase.getMethodName()+".snapshot.json");
			final Optional<String> snapshotOpt = FileUtils.findFileContent(snapshotFile);
			final String actualJson = JSONUtils.prettyPrint(actual);
			if (snapshotOpt.isPresent()) {
				final String normalized = JSONUtils.prettyPrint(snapshotOpt.get());
				Assertions.assertThat(actualJson).isEqualTo(normalized);
			} else {
				FileUtils.writeFile(snapshotFile, actualJson);
			}
	}
}