package test.internal.fileassertion.createnew;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.snapshot.api.Assertions;
import org.junit.jupiter.api.Test;
import test.internal.MockedFileUtilsTestBase;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

class MatchesSnapshot_record_new_Test extends MockedFileUtilsTestBase {
  @Test
  void testThatSnapshotCanBeRecorded() {
    final DummyObject given = TestDataFactory.createDummyObject();

    Assertions.assertThat(given) //
        .matchesSnapshot();

    assertThat(this.getCreatedDirs())
        .endsWith("src/test/java/test/internal/fileassertion/createnew/snapshots");
    assertThat(
            this.getWrittenContent(
                "test.internal.fileassertion.createnew.MatchesSnapshot_record_new_Test_testThatSnapshotCanBeRecorded.snapshot.json"))
        .isEqualToIgnoringWhitespace(
            """
		        		{
						  "someAttr1" : "abc",
						  "someAttr2" : 123
						}
		        		""");
  }
}
