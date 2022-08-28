package test;

import static org.assertj.snapshot.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MatchesSnapshot {

  @Test
  public void matchesSnapshot() {
    final String whatever = "hey oh";
    assertThat(whatever).matchesSnapshot();
  }
}
