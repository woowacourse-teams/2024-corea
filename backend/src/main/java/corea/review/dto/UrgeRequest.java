package corea.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 재촉 요청")
public record UrgeRequest(@Schema(description = "방 아이디", example = "1")
                            long roomId,

                          @Schema(description = "리뷰어 아이디", example = "2")
                            long reviewerId) {
}
