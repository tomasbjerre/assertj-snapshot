package org.assertj.snapshot.internal.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class FileUtilsImpl implements FileUtils {

  private static Logger LOG = Logger.getLogger(FileUtilsImpl.class.getName());
  private static FileUtils FILE_UTILS = new FileUtilsImpl();

  public FileUtilsImpl() {}

  public static FileUtils create() {
    return FILE_UTILS;
  }

  /** Visible for test */
  public static void setFileUtils(final FileUtils fileUtils) {
    LOG.info(FILE_UTILS.toString());
    FILE_UTILS = fileUtils;
  }

  @Override
  public Optional<String> findFileContent(final Path path) {
    if (this.pathExists(path)) {
      try {
        return Optional.of(Files.readString(path));
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    }
    return Optional.empty();
  }

  @Override
  public String getFileContent(final Path path) {
    final Optional<String> opt = this.findFileContent(path);
    if (opt.isPresent()) {
      return opt.get();
    }
    throw new RuntimeException(path + " not found");
  }

  @Override
  public void writeFileContent(final Path sourceFile, final String manipulatedSourceFileContent) {
    try {
      Files.writeString(sourceFile, manipulatedSourceFileContent);
    } catch (final IOException e) {
      throw new RuntimeException(sourceFile.toString(), e);
    }
  }

  @Override
  public void createDirs(final Path path) {
    if (!this.pathExists(path)) {
      try {
        Files.createDirectories(path);
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public boolean pathExists(final Path path) {
    return Files.exists(path);
  }
}
