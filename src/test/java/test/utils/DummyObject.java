package test.utils;

public class DummyObject {

  private final String someAttr1;
  private final Integer someAttr2;

  public DummyObject(final String someAttr1, final Integer someAttr2) {
    super();
    this.someAttr1 = someAttr1;
    this.someAttr2 = someAttr2;
  }

  public String getSomeAttr1() {
    return this.someAttr1;
  }

  public Integer getSomeAttr2() {
    return this.someAttr2;
  }
}
