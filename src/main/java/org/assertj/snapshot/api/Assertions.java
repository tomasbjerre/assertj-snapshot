package org.assertj.snapshot.api;

import org.assertj.snapshot.internal.assertions.SnapshotAssert;

public class Assertions {

  protected Assertions() {}

  public static SnapshotAssert assertThat(final Object actual) {
    return new SnapshotAssert(actual);
  }
}
