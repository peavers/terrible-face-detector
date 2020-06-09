/* Licensed under Apache-2.0 */
package io.terrible.face.controllers;

import io.terrible.face.domain.DetectRequest;
import io.terrible.face.services.FaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class FaceController {

  private final FaceService faceService;

  @PostMapping(value = "/faces", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<DetectRequest> findAll(@RequestBody final DetectRequest detectRequest) {

    return faceService.detect(detectRequest.getInput(), detectRequest.getOutput());
  }
}
