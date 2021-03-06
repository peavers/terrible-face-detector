/* Licensed under Apache-2.0 */
package io.terrible.face.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FaceCoordinates {
  private int x;

  private int y;

  private int width;

  private int height;
}
