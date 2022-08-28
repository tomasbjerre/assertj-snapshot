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
