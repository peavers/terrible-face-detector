/* Licensed under Apache-2.0 */
package io.terrible.face.configuration;

import io.terrible.face.properties.ClassifierProperties;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Slf4j
@Configuration
public class ClassifierConfig {

  @Bean
  public CascadeClassifier classifier(final ClassifierProperties classifierProperties) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    return load(classifierProperties.getClassifierPath());
  }

  private CascadeClassifier load(final String path) {

    try {
      final Resource classifierXml = new ClassPathResource(path);
      log.info("Using classifier {}", classifierXml.getFilename());
      return new CascadeClassifier(classifierXml.getFile().getAbsolutePath());
    } catch (final IOException e) {
      throw new RuntimeException("Abort. Unable to read a classifier");
    }
  }
}
