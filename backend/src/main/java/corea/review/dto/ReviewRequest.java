package corea.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 완료 처리 요청")
public record ReviewRequest(@Schema(description = "방 아이디", example = "1")
                            long roomId,

                            @Schema(description = "리뷰이 아이디", example = "2")
                            long revieweeId) {
}
