package org.assertj.snapshot.internal.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileUtils {

  private static FileUtils FILE_UTILS = new FileUtils();

  private FileUtils() {}

  public static FileUtils create() {
    return FILE_UTILS;
  }

  /** Visible for test */
  public static void setFileUtils(final FileUtils fileUtils) {
    FILE_UTILS = fileUtils;
  }

  public Optional<String> findFileContent(final Path path) {
    if (Files.exists(path)) {
      try {
        return Optional.of(Files.readString(path));
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    }
    return Optional.empty();
  }

  public String getFileContent(final Path path) {
    final Optional<String> opt = this.findFileContent(path);
    if (opt.isPresent()) {
      return opt.get();
    }
    throw new RuntimeException(path + " not found");
  }

  public void writeFileContent(final Path sourceFile, final String manipulatedSourceFileContent) {
    try {
      Files.writeString(sourceFile, manipulatedSourceFileContent);
    } catch (final IOException e) {
      throw new RuntimeException(sourceFile.toString(), e);
    }
  }

  public void createDirs(final Path path) {
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
