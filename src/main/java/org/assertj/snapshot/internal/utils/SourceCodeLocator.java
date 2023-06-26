package org.assertj.snapshot.internal.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class SourceCodeLocator {
  public static File getSourceFolder(final String className, final String fileName) {
    final String sourceCodePackageFolder = getPackageFolder(className);
    final Path findInFolder = Paths.get("src").toAbsolutePath();
    final List<Path> matchingFiles =
        getMatchingFiles(fileName, sourceCodePackageFolder, findInFolder);
    if (matchingFiles == null || matchingFiles.size() == 0) {
      throw new RuntimeException(
          "Cannot find " + className + " in " + findInFolder.toAbsolutePath());
    } else if (matchingFiles.size() > 1) {
      throw new RuntimeException(
          "Cannot find "
              + className
              + " in "
              + findInFolder.toAbsolutePath()
              + ". Found multiple matches: "
              + matchingFiles);
    }
    return matchingFiles.get(0).toFile().getParentFile();
  }

  private static List<Path> getMatchingFiles(
      final String fileName, final String sourceCodePackageFolder, final Path findInFolder) {
    try {
      return Files.find(findInFolder, Integer.MAX_VALUE, matches(fileName, sourceCodePackageFolder))
          .collect(Collectors.toList());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static BiPredicate<Path, BasicFileAttributes> matches(
      final String fileName, final String sourceCodePackageFolder) {
    return (file, attr) -> {
      final String dir = file.getParent().toFile().getAbsolutePath();
      final String name = file.toFile().getName();
      return dir.endsWith(sourceCodePackageFolder) && name.equals(fileName);
    };
  }

  private static String getPackageFolder(final String className) {
    final String[] packageAndClassName = className.split("\\.");
    return Arrays.asList(packageAndClassName).subList(0, packageAndClassName.length - 1).stream()
        .collect(Collectors.joining("/"));
  }
}
