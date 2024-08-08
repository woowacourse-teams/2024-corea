package corea.room.dto;

import corea.room.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "방 정보 응답")
public record RoomResponse(@Schema(description = "방 아이디", example = "1")
                           long id,

                           @Schema(description = "방 제목", example = "두근두근 코딩부")
                           String title,

                           @Schema(description = "방 내용", example = "MVC 패턴을 아시나요?")
                           String content,

                           @Schema(description = "방장 이름", example = "최진실")
                           String manager,

                           @Schema(description = "repository 링크", example = "https://github.com/example/java-racingcar")
                           String repositoryLink,

                           @Schema(description = "썸네일 링크", example = "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004")
                           String thumbnailLink,

                           @Schema(description = "상호 리뷰 인원", example = "2")
                           int matchingSize,

                           @Schema(description = "중심으로 리뷰하면 좋은 키워드", example = "[\"TDD\", \"클린코드\"]")
                           List<String> keywords,

                           @Schema(description = "현재 참여 인원", example = "29")
                           long currentParticipants,

                           @Schema(description = "제한 참여 인원", example = "200")
                           long limitedParticipants,

                           @Schema(description = "모집 마감일", example = "2024-07-30T15:00")
                           LocalDateTime recruitmentDeadline,

                           @Schema(description = "리뷰 마감일", example = "2024-08-10T23:59")
                           LocalDateTime reviewDeadline,

                           @Schema(description = "조회한 유저가 참여하고 있는 방인지 여부", example = "true")
                           boolean isParticipated,

                           @Schema(description = "모집 완료 여부", example = "false")
                           boolean isClosed
) {

    public static RoomResponse of(Room room, boolean isParticipated) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getContent(),
                room.getManagerName(),
                room.getRepositoryLink(),
                room.getThumbnailLink(),
                room.getMatchingSize(),
                room.getKeyword(),
                room.getCurrentParticipantsSize(),
                room.getLimitedParticipantsSize(),
                room.getRecruitmentDeadline(),
                room.getReviewDeadline(),
                isParticipated,
                room.isClosed()
        );
    }
}
