package test.examples;

import static org.assertj.snapshot.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

public class MatchesSnapshotTest {

  @Test
  public void matchesSnapshot() {
    final DummyObject whatever = TestDataFactory.createDummyObject();
    assertThat(whatever).matchesSnapshot();
  }
}
