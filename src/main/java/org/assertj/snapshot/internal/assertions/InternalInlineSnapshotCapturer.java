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
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

  private static final String INLINE_REGEXP = "\\.\s*matchesInlineSnapshot\\(\\)";
  private static final Pattern INLINE_PATTERN = Pattern.compile(INLINE_REGEXP);

  public static void assertEqual(final Object actual, final String expected) {
    if (expected == null || expected == CAPTURE_SNAPSHOT || expected.trim().isEmpty()) {
      InternalInlineSnapshotCapturer.captureInlineSnapshot(actual);
    } else {
      InternalAssertions.assertEqual(actual, expected);
    }
  }

  static void captureInlineSnapshot(final Object actual) {
    final AssertingTestCase testCase = TestCaseFinder.getTestCase();
    final File sourceFolderOfTestCase =
        SourceCodeLocator.getSourceFolder(testCase.getClassName(), testCase.getFile());
    final Path testCaseSourceFile =
        Paths.get(sourceFolderOfTestCase.getAbsolutePath(), testCase.getFile());
    final FileUtils fileUtils = FileUtilsImpl.create();
    final String testCaseContent = fileUtils.getFileContent(testCaseSourceFile);
    final Matcher matcherInline = INLINE_PATTERN.matcher(testCaseContent);
    if (!matcherInline.find()) {
      throw new RuntimeException("Cannot find " + INLINE_REGEXP + " in " + testCaseContent);
    }
    final String jsonToUseAsExpected = JSONUtils.prettyPrint(actual);
    final String manipulatedTestCaseContent =
        matcherInline.replaceFirst(
            "\\.matchesInlineSnapshot(\"\"\"\n" + jsonToUseAsExpected + "\n\"\"\")");
    fileUtils.writeFileContent(testCaseSourceFile, manipulatedTestCaseContent);
  }
}
