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
