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
package test.internal.inlineassertion;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.snapshot.api.Assertions;
import org.junit.jupiter.api.Test;
import test.internal.MockedFileUtilsTestBase;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

class MatchesInlineSnapshot_next_line_Test extends MockedFileUtilsTestBase {
  @Test
  void testThatInlineSnapshotCanBeUpdated_next_line() {
    final DummyObject given = TestDataFactory.createDummyObject();

    Assertions.assertThat(given).matchesInlineSnapshot();

    this.removeFileUtilsMock();
    assertThat(this.getCreatedDirs()).isEqualTo(null);
    Assertions.assertThat(this.getWrittenContent()).matchesSnapshot();
  }
}
