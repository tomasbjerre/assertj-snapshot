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
package test.internal.fileassertion.createnew;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.snapshot.api.Assertions;
import org.junit.jupiter.api.Test;
import test.internal.MockedFileUtilsTest;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

class MatchesSnapshotTest_record_new extends MockedFileUtilsTest {
  @Test
  void testThatSnapshotCanBeRecorded() {
    final DummyObject given = TestDataFactory.createDummyObject();

    Assertions.assertThat(given) //
        .matchesSnapshot();

    assertThat(this.getCreatedDirs())
        .endsWith("src/test/java/test/internal/fileassertion/createnew/snapshots");
    assertThat(
            this.getWrittenContent(
                "test.internal.fileassertion.createnew.MatchesSnapshotTest_record_new_testThatSnapshotCanBeRecorded.snapshot.json"))
        .isEqualToIgnoringWhitespace(
            """
		        		{
						  "someAttr1" : "abc",
						  "someAttr2" : 123
						}
		        		""");
  }
}
