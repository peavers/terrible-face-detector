/* Licensed under Apache-2.0 */
package io.terrible.face.services;

import io.terrible.face.domain.DetectRequest;
import io.terrible.face.utils.ClassifierUtils;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FaceServiceImpl implements FaceService {

  private final CascadeClassifier classifier;

  private final ImageService imageService;

  /**
   * Important constructor which loads in the OpenCV native library and decides what classifier to
   * load.
   */
  public FaceServiceImpl(final ImageService imageService) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    this.classifier = ClassifierUtils.load(ClassifierUtils.FRONTAL_FACE_ALT);
    this.imageService = imageService;
  }

  @Override
  public Flux<DetectRequest> detect(final String input, final String output) {
    return imageService.findImages(Paths.get(input)).flatMap(this::detectFaces);
  }

  /** Here is the actual workhorse of this service. */
  private Mono<DetectRequest> detectFaces(final String input) {

    final Mat grayFrame = new Mat();
    final MatOfRect faces = new MatOfRect();
    final Mat src = Imgcodecs.imread(input);

    Imgproc.cvtColor(src, grayFrame, Imgproc.COLOR_BGR2GRAY);
    Imgproc.equalizeHist(grayFrame, grayFrame);

    classifier.detectMultiScale(grayFrame, faces, 1.1, 2);

    // Don't create images with no faces detected
    if (faces.empty()) return Mono.empty();

    drawBoxes(faces, src);

    final ResponseBuilder responseBuilder = new ResponseBuilder(input, faces).build();

    return Imgcodecs.imwrite(responseBuilder.getOutput(), src)
        ? Mono.just(responseBuilder.getResult())
        : Mono.empty();
  }

  /**
   * Draws the images around a new copy of the image. This will draw multiple boxes if multiple
   * faces are found on the image.
   */
  private void drawBoxes(final MatOfRect faceDetections, final Mat src) {

    Arrays.stream(faceDetections.toArray())
        .forEach(
            rect ->
                Imgproc.rectangle(
                    src,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0),
                    2));
  }

  /** Builds the response object. */
  @RequiredArgsConstructor
  private static class ResponseBuilder {

    private final String input;

    private final MatOfRect faces;

    @Getter private String output;

    @Getter private DetectRequest result;

    public ResponseBuilder build() {
      output =
          String.format(
              "%s/%s-terrible.jpg", new File(input).getParent(), FilenameUtils.getBaseName(input));

      result =
          DetectRequest.builder()
              .input(input)
              .output(output)
              .faceCount(faces.toList().size())
              .build();

      return this;
    }
  }
}
