/* Licensed under Apache-2.0 */
package io.terrible.face.controllers;

import io.terrible.face.domain.FaceCoordinates;
import io.terrible.face.services.FaceService;
import java.io.IOException;
import java.util.ArrayDeque;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class FaceController {

  private final FaceService faceService;

  /**
   * Given a file, it will return an array of face boxes detected in the file (if any). Each box
   * contains the X and Y coordinate along with the width and the height of the face box detected.
   */
  @PostMapping("/faces")
  public ResponseEntity<ArrayDeque<FaceCoordinates>> detectFace(
      @RequestParam("file") final MultipartFile input) throws IOException {

    final ArrayDeque<FaceCoordinates> faceCoordinates = faceService.detect(input);

    return new ResponseEntity<>(faceCoordinates, HttpStatus.ACCEPTED);
  }
}
