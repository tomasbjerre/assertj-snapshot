package org.assertj.snapshot.internal.utils;

public class TestCaseFinder {
  public static class AssertingTestCase {

    private final String className;
    private final String file;
    private final String methodName;
    private final int lineNumber;

    AssertingTestCase(
        final String className, final String file, final String methodName, final int lineNumber) {
      this.className = className;
      this.file = file;
      this.methodName = methodName;
      this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
      return this.lineNumber;
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
          + ", lineNumber="
          + this.lineNumber
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
      final int lineNumber = element.getLineNumber();
      if (className.startsWith("org.assertj")) {
        continue;
      }
      return new AssertingTestCase(className, file, methodName, lineNumber);
    }
    throw new RuntimeException("Cannot find test case");
  }
}
