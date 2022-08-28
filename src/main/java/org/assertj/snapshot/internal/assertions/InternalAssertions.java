/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2022-2022 the original author or authors.
 */
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
