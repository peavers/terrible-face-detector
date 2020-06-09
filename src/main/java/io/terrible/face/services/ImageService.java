/* Licensed under Apache-2.0 */
package io.terrible.face.services;

import java.nio.file.Path;
import reactor.core.publisher.Flux;

public interface ImageService {

  /**
   * Walk over each file in the directory, first check if we have read access; Check if the file is
   * of a known image type and return all found.
   *
   * <p>NOTE: This is a heavy blocking call due to the IO operations of the file walk call.
   */
  Flux<String> findImages(Path inputDirectory);
}
