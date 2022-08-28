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
package test.internal;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.assertj.snapshot.internal.utils.FileUtils;
import org.assertj.snapshot.internal.utils.FileUtilsImpl;

public class MockedFileUtils implements FileUtils {
  private Path createDirs;
  private final Map<String, String> writeFileContentByFileName = new HashMap<>();
  private final FileUtilsImpl fileUtilsImpl;

  public MockedFileUtils() {
    this.fileUtilsImpl = new FileUtilsImpl();
  }

  @Override
  public void createDirs(final Path path) {
    this.createDirs = path;
  }

  @Override
  public Optional<String> findFileContent(final Path path) {
    return this.fileUtilsImpl.findFileContent(path);
  }

  @Override
  public String getFileContent(final Path path) {
    return this.fileUtilsImpl.getFileContent(path);
  }

  @Override
  public boolean pathExists(final Path path) {
    return this.fileUtilsImpl.pathExists(path);
  }

  @Override
  public void writeFileContent(final Path path, final String content) {
    final String key = this.getKey(path);
    this.writeFileContentByFileName.put(key, content);
  }

  private String getKey(final Path path) {
    return path.toFile().getName();
  }

  public Path getCreateDirs() {
    return this.createDirs;
  }

  public Map<String, String> getWriteFileContentByFileName() {
    return this.writeFileContentByFileName;
  }

  @Override
  public String toString() {
    return "MockedFileUtils [createDirs="
        + this.createDirs
        + ", writeFileContentByFileName="
        + this.writeFileContentByFileName
        + ", fileUtilsImpl="
        + this.fileUtilsImpl
        + "]";
  }
}
