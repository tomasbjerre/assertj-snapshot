package test.internal.inlineassertion;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.snapshot.api.Assertions;
import org.junit.jupiter.api.Test;
import test.internal.MockedFileUtilsTest;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

class MatchesInlineSnapshotTest_next_line extends MockedFileUtilsTest {
  @Test
  void testThatInlineSnapshotCanBeUpdated_next_line() {
    final DummyObject given = TestDataFactory.createDummyObject();

    Assertions.assertThat(given).matchesInlineSnapshot();

    this.removeFileUtilsMock();
    assertThat(this.getCreatedDirs()).isEqualTo(null);
    Assertions.assertThat(this.getWrittenContent()).matchesSnapshot();
  }
}
