/* Licensed under Apache-2.0 */
package io.terrible.face.services;

import io.terrible.face.domain.FaceCoordinates;
import java.io.IOException;
import java.util.ArrayDeque;
import org.springframework.web.multipart.MultipartFile;

public interface FaceService {

  ArrayDeque<FaceCoordinates> detect(MultipartFile multipartFile) throws IOException;
}
