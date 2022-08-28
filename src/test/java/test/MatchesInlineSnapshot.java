package test;

import static org.assertj.snapshot.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MatchesInlineSnapshot {

  @Test
  public void matchesInlineSnapshot() {
    final String whatever = "hey oh";
    assertThat(whatever).matchesInlineSnapshot("""
				"hey oh"
				""");
  }
}
