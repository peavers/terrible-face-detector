/* Licensed under Apache-2.0 */
package io.terrible.face.utils;

import java.io.IOException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Slf4j
@UtilityClass
public class ClassifierUtils {

  public final String FRONTAL_FACE_ALT = "haarcascades/haarcascade_frontalface_alt.xml";

  public CascadeClassifier load(final String path) {

    try {
      final Resource classifierXml = new ClassPathResource(path);

      log.info("Using classifier {}", classifierXml.getFilename());

      return new CascadeClassifier(classifierXml.getFile().getAbsolutePath());

    } catch (final IOException e) {
      throw new RuntimeException("Abort. Unable to read a classifier");
    }
  }
}
