package test.internal.fileassertion.existing;

import org.assertj.snapshot.api.Assertions;
import org.junit.jupiter.api.Test;

import test.internal.MockedFileUtilsTest;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

class MatchesSnapshotTest_verify_existing extends MockedFileUtilsTest {
  @Test
  void testThatSnapshotCanBeRead() {
    final DummyObject given = TestDataFactory.createDummyObject();
    Assertions.assertThat(given) //
        .matchesSnapshot();

    this.assertNoWrittenContent();
  }
}
