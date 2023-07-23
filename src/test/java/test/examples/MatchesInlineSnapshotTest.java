package test.examples;

import static org.assertj.snapshot.api.Assertions.assertThat;
import org.assertj.snapshot.api.UpdateMode;
import org.junit.jupiter.api.Test;
import test.utils.DummyObject;
import test.utils.TestDataFactory;

public class MatchesInlineSnapshotTest {

    @Test
    public void matchesInlineSnapshot1() {
        final DummyObject whatever = TestDataFactory.createDummyObject();
        assertThat(whatever).matchesInlineSnapshot("""
            {
              "someAttr1" : "abc",
              "someAttr2" : 123
            }
            """);
    }

    @Test
    public void matchesInlineSnapshot2() {
        final DummyObject whatever2 = TestDataFactory.createAnotherDummyObject();
        assertThat(whatever2).matchesInlineSnapshot("""
            {
              "someAttr1" : "def",
              "someAttr2" : 456
            }
            """);
    }

    @Test
    public void matchesInlineSnapshot_double_assert() {
        final DummyObject whatever1 = TestDataFactory.createDummyObject();
        assertThat(whatever1).matchesInlineSnapshot("""
            {
              "someAttr1" : "abc",
              "someAttr2" : 123
            }
            """);
        final DummyObject whatever2 = TestDataFactory.createAnotherDummyObject();
        assertThat(whatever2).matchesInlineSnapshot("""
            {
              "someAttr1" : "def",
              "someAttr2" : 456
            }
            """);
    }

    @Test
    public void matchesInlineSnapshot_always_update() {
        final DummyObject whatever1 = TestDataFactory.createDummyObject();
        assertThat(whatever1).withUpdateMode(UpdateMode.UPDATE_ALWAYS).matchesInlineSnapshot("""
            {
              "someAttr1" : "abc",
              "someAttr2" : 123
            }
            """);
    }
}
