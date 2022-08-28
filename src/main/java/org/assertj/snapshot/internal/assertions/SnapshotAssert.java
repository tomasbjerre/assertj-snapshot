package org.assertj.snapshot.internal.assertions;

import org.assertj.core.api.AbstractAssert;

public class SnapshotAssert extends AbstractAssert<SnapshotAssert, Object> {

  public SnapshotAssert(final Object actual) {
    super(actual, SnapshotAssert.class);
  }

  public void matchesInlineSnapshot() {
    this.matchesInlineSnapshot(InternalInlineSnapshotCapturer.CAPTURE_SNAPSHOT);
  }

  public void matchesInlineSnapshot(final String expected) {
    InternalInlineSnapshotCapturer.assertEqual(this.actual, expected);
  }

  public void matchesSnapshot() {
    InternalFileAssertions.assertEqual(this.actual);
  }
}
