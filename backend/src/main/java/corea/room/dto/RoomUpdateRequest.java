package corea.room.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import corea.member.domain.Member;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "방 수정 요청")
public record RoomUpdateRequest(@Schema(description = "방 ID", example = "99")
                                @NotNull
                                long roomId,

                                @Schema(description = "방 제목", example = "MVC를 아시나요?")
                                @NotBlank
                                String title,

                                @Schema(description = "방 내용", example = "MVC 패턴을 아시나요?")
                                String content,

                                @Schema(description = "repository 링크", example = "https://github.com/example/java-racingcar")
                                @NotBlank
                                String repositoryLink,

                                @Schema(description = "썸네일 링크", example = "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004")
                                String thumbnailLink,

                                @Schema(description = "상호 리뷰 인원", example = "2")
                                @NotNull
                                int matchingSize,

                                @Schema(description = "중심으로 리뷰하면 좋은 키워드", example = "[\"TDD\", \"클린코드\"]")
                                List<String> keywords,

                                @Schema(description = "제한 참여 인원", example = "200")
                                @NotNull
                                int limitedParticipants,

                                @Schema(description = "모집 마감일", example = "2024-07-30 15:00")
                                @NotNull
                                @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                                LocalDateTime recruitmentDeadline,

                                @Schema(description = "리뷰 마감일", example = "2024-08-10 23:59")
                                @NotNull
                                @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                                LocalDateTime reviewDeadline,

                                @Schema(description = "방이 속하는 분야", example = "BE")
                                @NotNull
                                RoomClassification classification
) {

    private static final RoomStatus INITIAL_ROOM_STATUS = RoomStatus.OPEN;

    public Room toEntity(Room room, Member member) {
        return new Room(
                roomId,
                title, content,
                matchingSize, repositoryLink,
                thumbnailLink, keywords,
                room.getCurrentParticipantsSize(), limitedParticipants,
                member, recruitmentDeadline,
                reviewDeadline, classification,
                INITIAL_ROOM_STATUS
        );
    }
}
