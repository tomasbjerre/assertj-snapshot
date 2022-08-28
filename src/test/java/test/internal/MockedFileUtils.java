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
