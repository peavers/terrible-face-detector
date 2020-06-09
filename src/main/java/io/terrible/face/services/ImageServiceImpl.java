/* Licensed under Apache-2.0 */
package io.terrible.face.services;

import com.google.common.net.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  @Override
  public Flux<String> findImages(final Path inputDirectory) {

    try {
      return Flux.fromStream(
          Files.walk(inputDirectory) // Blocking
              .filter(Files::isReadable)
              .filter(path -> !Files.isDirectory(path))
              .filter(path -> detectMimeType(probeContentType(path)))
              .map(Path::toString));

    } catch (final IOException e) {
      log.warn("Issue processing stream {} {}", e.getCause(), e);
      return Flux.error(e);
    }
  }

  /**
   * Make sure we accept file where the mimeType matches any known image type according to Googles
   * Guava implementation. If we find a match, return true. Otherwise false and the file is skipped.
   */
  private static boolean detectMimeType(final String mimeType) {
    //noinspection UnstableApiUsage
    return MediaType.parse(mimeType).is(MediaType.ANY_IMAGE_TYPE);
  }

  /**
   * Probe the file at the given path, for the content type of the file, or null if the content type
   * cannot be determined
   */
  private static String probeContentType(final Path path) {

    try {
      return Files.probeContentType(path);
    } catch (final IOException e) {
      log.error("Unable to probe {} {}", path, e.getMessage());
      return null;
    }
  }
}
