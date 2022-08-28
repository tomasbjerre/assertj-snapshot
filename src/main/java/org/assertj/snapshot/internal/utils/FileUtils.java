package org.assertj.snapshot.internal.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileUtils {

  public static String readFile(final Path path) {
final Optional<String> opt = findFileContent(path);
if (opt.isPresent()) {
	return opt.get();
}
throw new RuntimeException(path+" not found");
  }

  public static void writeFile(final Path sourceFile, final String manipulatedSourceFileContent) {
    try {
      Files.writeString(sourceFile, manipulatedSourceFileContent);
    } catch (final IOException e) {
      throw new RuntimeException(sourceFile.toString(), e);
    }
  }

public static void createDirs(final Path path) {
	if (!Files.exists(path)) {
		try {
			Files.createDirectories(path);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
	  }

public static Optional<String> findFileContent(final Path path) {
	if (Files.exists(path)) {
		try {
return			Optional.of(Files.readString(path));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
	return Optional.empty();
}
}
