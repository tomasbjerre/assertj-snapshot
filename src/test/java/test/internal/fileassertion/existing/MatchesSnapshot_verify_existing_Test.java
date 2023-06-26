package test.internal.fileassertion.existing;

import org.assertj.snapshot.api.Assertions;
import org.junit.jupiter.api.Test;
import test.internal.MockedFileUtilsTestBase;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

class MatchesSnapshot_verify_existing_Test extends MockedFileUtilsTestBase {
  @Test
  void testThatSnapshotCanBeRead() {
    final DummyObject given = TestDataFactory.createDummyObject();
    Assertions.assertThat(given) //
        .matchesSnapshot();

    this.assertNoWrittenContent();
  }
}
