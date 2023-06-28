package test.utils;

public class TestDataFactory {

  public static DummyObject createDummyObject() {
    return new DummyObject("abc", 123);
  }

  public static DummyObject createAnotherDummyObject() {
    return new DummyObject("def", 456);
  }
}
