package org.assertj.snapshot.internal.assertions;

import org.assertj.core.api.AbstractAssert;

public class SnapshotAssert extends AbstractAssert<SnapshotAssert, Object> {

  private UPDATE_MODE updateMode = UPDATE_MODE.UPDATE_IF_NO_PREVIOUS_SNAPSHOT;

  public SnapshotAssert(final Object actual) {
    super(actual, SnapshotAssert.class);
  }

  public SnapshotAssert withUpdateMode(final UPDATE_MODE updateMode) {
    this.updateMode = updateMode;
    return this;
  }

  public void matchesInlineSnapshot() {
    this.matchesInlineSnapshot(InternalInlineSnapshotCapturer.CAPTURE_SNAPSHOT);
  }

  public void matchesInlineSnapshot(final String expected) {
    InternalInlineSnapshotCapturer.assertEqual(this.actual, expected, this.updateMode);
  }

  public void matchesSnapshot() {
    InternalFileAssertions.assertEqual(this.actual, this.updateMode);
  }
}
