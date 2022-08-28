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
package org.assertj.snapshot.internal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String prettyPrint(final String jsonString) {
    final Object deserialized = readValue(jsonString, Object.class);
    return writeValueAsString(deserialized);
  }

  public static String prettyPrint(final Object object) {
    return writeValueAsString(object);
  }

  private static String writeValueAsString(final Object object) {
    try {
      return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (final Exception e) {
      throw new RuntimeException("From: " + object, e);
    }
  }

  private static Object readValue(final String jsonString, final Class<?> clazz) {
    try {
      return OBJECT_MAPPER.readValue(jsonString, clazz);
    } catch (final Exception e) {
      throw new RuntimeException(jsonString, e);
    }
  }
}
