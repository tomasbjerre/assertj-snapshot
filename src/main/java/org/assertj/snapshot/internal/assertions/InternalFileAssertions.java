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

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.snapshot.internal.utils.FileUtils;
import org.assertj.snapshot.internal.utils.FileUtilsImpl;
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
    final FileUtils fileUtils = FileUtilsImpl.create();
    if (!fileUtils.pathExists(snapshotsFolder)) {
      fileUtils.createDirs(snapshotsFolder);
    }
    final Path snapshotFile =
        snapshotsFolder.resolve(
            testCase.getClassName() + "_" + testCase.getMethodName() + ".snapshot.json");
    final Optional<String> snapshotOpt = fileUtils.findFileContent(snapshotFile);
    final String actualJson = JSONUtils.prettyPrint(actual);
    if (snapshotOpt.isPresent()) {
      final String normalized = JSONUtils.prettyPrint(snapshotOpt.get());
      Assertions.assertThat(actualJson).isEqualTo(normalized);
    } else {
      fileUtils.writeFileContent(snapshotFile, actualJson);
    }
  }
}
