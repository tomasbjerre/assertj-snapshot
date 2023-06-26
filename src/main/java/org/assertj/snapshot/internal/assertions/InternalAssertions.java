package org.assertj.snapshot.internal.assertions;

import org.assertj.core.api.Assertions;
import org.assertj.snapshot.internal.utils.JSONUtils;

class InternalAssertions {

  static void assertEqual(final Object actual, final String expected) {
    final String normalizedExpected = JSONUtils.prettyPrint(expected);
    final String normalizedActual = JSONUtils.prettyPrint(actual);
    Assertions.assertThat(normalizedActual).isEqualTo(normalizedExpected);
  }
}
