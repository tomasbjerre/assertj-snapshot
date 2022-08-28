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

public class TestCaseFinder {
  public static class AssertingTestCase {

    private final String className;
    private final String file;
    private final String methodName;

    AssertingTestCase(final String className, final String file, final String methodName) {
      this.className = className;
      this.file = file;
      this.methodName = methodName;
    }

    public String getClassName() {
      return this.className;
    }

    public String getFile() {
      return this.file;
    }

    public String getMethodName() {
      return this.methodName;
    }

    @Override
    public String toString() {
      return "AssertingTestCase [className="
          + this.className
          + ", file="
          + this.file
          + ", methodName="
          + this.methodName
          + "]";
    }
  }

  public static AssertingTestCase getTestCase() {
    final Exception exception = new Exception();
    final StackTraceElement[] stackTrace = exception.getStackTrace();
    for (final StackTraceElement element : stackTrace) {
      final String file = element.getFileName();
      final String methodName = element.getMethodName();
      final String className = element.getClassName();
      if (className.startsWith("org.assertj")) {
        continue;
      }
      return new AssertingTestCase(className, file, methodName);
    }
    throw new RuntimeException("Cannot find test case");
  }
}
