package org.assertj.snapshot.api;

public enum UpdateMode {
  /**
   * The default mode. Assertions are made against recorded snapshots, if any. If no recorded
   * snapshots, they are created.
   */
  UPDATE_IF_NO_PREVIOUS_SNAPSHOT,
  /**
   * Always update the snapshot. This means the tests will never fail, you will have to manually
   * review the changes.
   */
  UPDATE_ALWAYS,
}
