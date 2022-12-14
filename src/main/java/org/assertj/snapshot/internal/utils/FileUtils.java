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
