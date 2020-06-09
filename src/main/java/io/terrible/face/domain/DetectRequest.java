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
public class DetectRequest {

  private String input;

  private String output;

  private int faceCount;
}
