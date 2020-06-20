/* Licensed under Apache-2.0 */
package io.terrible.face.extensions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoDeletingFile implements AutoCloseable {

  private final Path file;

  public AutoDeletingFile() throws IOException {
    file = Files.createTempFile(null, null);
  }

  public File getFile() {
    return file.toFile();
  }

  @Override
  public void close() throws IOException {
    Files.deleteIfExists(file);
  }
}
