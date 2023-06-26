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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class FileUtilsImpl implements FileUtils {

  private static Logger LOG = Logger.getLogger(FileUtilsImpl.class.getName());
  private static FileUtils FILE_UTILS = new FileUtilsImpl();

  public FileUtilsImpl() {}

  @SuppressFBWarnings
  public static FileUtils create() {
    return FILE_UTILS;
  }

  /** Visible for test */
  @SuppressFBWarnings
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
