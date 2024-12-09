package corea.room.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "방 생성 요청")
public record RoomRequest(
        RoomInfoRequest roomInfoRequest,
        DeadlineRequest deadlineRequest,
        RepositoryRequest repositoryRequest,
        ManagerParticipationRequest managerParticipationRequest
) {
    public record RoomInfoRequest(
            @Schema(description = "방 제목", example = "MVC를 아시나요?")
            @NotBlank
            String title,

            @Schema(description = "방 내용", example = "MVC 패턴을 아시나요?")
            String content,

            @Schema(description = "썸네일 링크", example = "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004")
            String thumbnailLink,

            @Schema(description = "상호 리뷰 인원", example = "2")
            @NotNull
            int matchingSize,

            @Schema(description = "중심으로 리뷰하면 좋은 키워드", example = "[\"TDD\", \"클린코드\"]")
            List<String> keywords,

            @Schema(description = "제한 참여 인원", example = "200")
            @NotNull
            int limitedParticipants
    ) {
    }

    public record DeadlineRequest(
            @Schema(description = "모집 마감일", example = "2024-07-30 15:00")
            @NotNull
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime recruitmentDeadline,

            @Schema(description = "리뷰 마감일", example = "2024-08-10 23:59")
            @NotNull
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime reviewDeadline
    ) {
    }

    public record RepositoryRequest(
            @Schema(description = "repository 링크", example = "https://github.com/example/java-racingcar")
            @NotBlank
            String repositoryLink,

            @Schema(description = "방이 속하는 분야", example = "BACKEND")
            @NotNull
            RoomClassification classification,

            @Schema(description = "어떤 저장소에서 확인하는지", example = "true")
            boolean isPublic
    ) {
    }

    public record ManagerParticipationRequest(
            @Schema(description = "참여자 역할", example = "REVIEWER")
            MemberRole memberRole,

            @Schema(description = "참여자가 원하는 매칭 인원 수", example = "3")
            int matchingSize
    ) {
    }

    private static final int INITIAL_REVIEWER_COUNT = 0;
    private static final int INITIAL_BOTH_COUNT = 0;
    private static final RoomStatus INITIAL_ROOM_STATUS = RoomStatus.OPEN;

    public Room toEntity(Member manager) {
        return new Room(
                roomInfoRequest.title(), roomInfoRequest.content(), roomInfoRequest.matchingSize(),
                repositoryRequest.repositoryLink(),
                roomInfoRequest.thumbnailLink(), roomInfoRequest.keywords(),
                INITIAL_REVIEWER_COUNT, INITIAL_BOTH_COUNT,
                roomInfoRequest.limitedParticipants(), manager,
                deadlineRequest.recruitmentDeadline(), deadlineRequest.reviewDeadline(),
                repositoryRequest.classification(),
                INITIAL_ROOM_STATUS
        );
    }

    @JsonIgnore
    public boolean isPublic() {
        return repositoryRequest().isPublic();
    }
}
