package org.assertj.snapshot.internal.utils;

import java.nio.file.Path;
import java.util.Optional;

/** This interface is added to ease mocking file operations in test cases. */
public interface FileUtils {

  void createDirs(Path path);

  Optional<String> findFileContent(Path path);

  String getFileContent(Path path);

  void writeFileContent(Path path, String content);

  boolean pathExists(Path path);
}
