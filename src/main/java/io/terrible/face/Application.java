/* Licensed under Apache-2.0 */
package io.terrible.face;

import io.terrible.face.properties.ClassifierProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ClassifierProperties.class)
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
