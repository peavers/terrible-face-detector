/* Licensed under Apache-2.0 */
package io.terrible.face.services;

import io.terrible.face.domain.FaceCoordinates;
import io.terrible.face.extensions.AutoDeletingFile;
import io.terrible.face.properties.ClassifierProperties;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceServiceImpl implements FaceService {

  private final ClassifierProperties classifierProperties;

  private final CascadeClassifier classifier;

  @Override
  public ArrayDeque<FaceCoordinates> detect(final MultipartFile multipartFile) throws IOException {

    try (final AutoDeletingFile tempFile = new AutoDeletingFile()) {
      final File file = tempFile.getFile();

      multipartFile.transferTo(file);

      return detectFaces(file);
    }
  }

  /** Here is the actual workhorse of this service. */
  private ArrayDeque<FaceCoordinates> detectFaces(final File input) {
    final Mat grayFrame = new Mat();
    final MatOfRect faces = new MatOfRect();
    final Mat src = Imgcodecs.imread(input.getAbsolutePath());

    Imgproc.cvtColor(src, grayFrame, Imgproc.COLOR_BGR2GRAY);
    Imgproc.equalizeHist(grayFrame, grayFrame);

    classifier.detectMultiScale(
        grayFrame,
        faces,
        classifierProperties.getScaleFactor(),
        classifierProperties.getMinNeighbours(),
        classifierProperties.getFlags(),
        classifierProperties.getMinFaceSize(),
        classifierProperties.getMaxFaceSize());

    // Don't return images with no faces detected
    if (faces.empty()) return new ArrayDeque<>();

    return buildFaceBox(faces);
  }

  /**
   * Save the location of where each face is found. Will save multiple locations, if multiple faces
   * are found in the image
   */
  private ArrayDeque<FaceCoordinates> buildFaceBox(final MatOfRect faceDetections) {

    final ArrayDeque<FaceCoordinates> faceCoordinates =
        new ArrayDeque<>(faceDetections.toArray().length);

    Arrays.stream(faceDetections.toArray())
        .map(
            rect ->
                FaceCoordinates.builder()
                    .x(rect.x)
                    .y(rect.y)
                    .width(rect.width)
                    .height(rect.height)
                    .build())
        .forEach(faceCoordinates::push);

    return faceCoordinates;
  }
}
