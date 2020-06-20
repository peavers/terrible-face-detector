/* Licensed under Apache-2.0 */
package io.terrible.face.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opencv.core.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("classifier")
public class ClassifierProperties {

  /**
   * Parameter with the same meaning for an old cascade as in the function cvHaarDetectObjects. It
   * is not used for a new cascade.
   */
  private int flags;

  /** Parameter specifying how many neighbors each candidate rectangle should have to retain it. */
  private int minNeighbours;

  /** Parameter specifying how much the image size is reduced at each image scale. */
  private double scaleFactor;

  /** minSize Minimum possible object size. Objects smaller than that are ignored. */
  private Size minFaceSize = new Size(10, 10);

  /**
   * Maximum possible object size. Objects larger than that are ignored. If {@code maxSize * ==
   * minSize} model is evaluated on single scale.
   */
  private Size maxFaceSize = new Size(200, 200);

  /**
   * The type of classifier to use. Check the resource directories for each one. Must be relative
   * path, for example "haarcascades/haarcascade_frontalface_default.xml"
   */
  private String classifierPath;
}
