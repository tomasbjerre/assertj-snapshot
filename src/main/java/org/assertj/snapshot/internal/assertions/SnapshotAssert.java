package org.assertj.snapshot.internal.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.snapshot.api.UpdateMode;

public class SnapshotAssert extends AbstractAssert<SnapshotAssert, Object> {

  private UpdateMode updateMode = UpdateMode.UPDATE_IF_NO_PREVIOUS_SNAPSHOT;

  public SnapshotAssert(final Object actual) {
    super(actual, SnapshotAssert.class);
  }

  public SnapshotAssert withUpdateMode(final UpdateMode updateMode) {
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
