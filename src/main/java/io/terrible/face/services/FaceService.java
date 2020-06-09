/* Licensed under Apache-2.0 */
package io.terrible.face.services;

import io.terrible.face.domain.DetectRequest;
import reactor.core.publisher.Flux;

public interface FaceService {

  Flux<DetectRequest> detect(String input, String output);
}
