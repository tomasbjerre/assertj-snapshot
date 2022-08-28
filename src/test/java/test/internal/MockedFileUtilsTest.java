package test.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.Map;
import org.assertj.snapshot.internal.utils.FileUtilsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class MockedFileUtilsTest {
  private MockedFileUtils fileUtils;

  @BeforeEach
  public void before() {
    this.fileUtils = new MockedFileUtils();
    FileUtilsImpl.setFileUtils(this.fileUtils);
  }

  @AfterEach
  public void after() {
    this.removeFileUtilsMock();
  }

  public void removeFileUtilsMock() {
    FileUtilsImpl.setFileUtils(new FileUtilsImpl());
  }

  public MockedFileUtils getMockedFileUtils() {
    return this.fileUtils;
  }

  public String getCreatedDirs() {
    final Path createDirs = this.getMockedFileUtils().getCreateDirs();
    if (createDirs == null) {
      return null;
    }
    return createDirs.toString();
  }

  public String getWrittenContent() {
    final Exception exception = new Exception();
    final String file = exception.getStackTrace()[1].getFileName();
    return this.getWrittenContent(file);
  }

  public void assertNoWrittenContent() {
    final Map<String, String> writtenFiles =
        this.getMockedFileUtils().getWriteFileContentByFileName();
    assertThat(writtenFiles).isEmpty();
    assertThat(this.getCreatedDirs()).isNull();
  }

  public String getWrittenContent(final String file) {
    final Map<String, String> map = this.getMockedFileUtils().getWriteFileContentByFileName();
    if (!map.containsKey(file)) {
      throw new RuntimeException(file + " not found in " + map);
    }
    return map.get(file);
  }
}
