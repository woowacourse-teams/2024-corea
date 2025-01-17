package corea.room.dto;

import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomMatchInfo;
import corea.room.domain.RoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record RefactorRoomResponse(
        RoomInfoResponse roomInfoResponse,
        DeadlineResponse deadlineResponse,
        RepositoryResponse repositoryResponse,
        ParticipationResponse participationResponse
) {
    public record RoomInfoResponse(
            @Schema(description = "방 아이디", example = "1")
            long roomId,

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
            int limitedParticipants,

            @Schema(description = "리뷰어 참여 수", example = "10")
            @NotNull
            int reviewerCount,

            @Schema(description = "일반 참여 수", example = "123")
            @NotNull
            int bothCount,

            @Schema(description = "방장 이름", example = "최진실")
            String manager,

            @Schema(description = "방 상태", example = "OPEN")
            RoomStatus roomStatus,

            @Schema(description = "매칭 실패 원인 메세지 제공", example = "참여 인원이 부족하여 매칭을 진행할 수 없습니다.")
            String message
    ) {
        public static RoomInfoResponse from(Room room) {
            return new RoomInfoResponse(
                    room.getId(),
                    room.getTitle(),
                    room.getContent(),
                    room.getThumbnailLink(),
                    room.getMatchingSize(),
                    room.getKeyword(),
                    room.getLimitedParticipantsSize(),
                    room.getReviewerCount(),
                    room.getBothCount(),
                    room.getManagerName(),
                    room.getRoomStatus(),
                    ""
            );
        }
    }

    public record DeadlineResponse(
            @Schema(description = "모집 마감일", example = "2024-11-28T11:10:00")
            LocalDateTime recruitmentDeadline,

            @Schema(description = "리뷰 마감일", example = "2024-11-28T11:20:00")
            LocalDateTime reviewDeadline
    ) {
        public static DeadlineResponse from(Room room) {
            return new DeadlineResponse(
                    room.getRecruitmentDeadline(),
                    room.getReviewDeadline()
            );
        }
    }

    public record RepositoryResponse(
            @Schema(description = "repository 링크", example = "https://github.com/example/java-racingcar")
            @NotBlank
            String repositoryLink,

            @Schema(description = "방이 속하는 분야", example = "BE")
            @NotNull
            RoomClassification classification,

            @Schema(description = "어떤 저장소에서 확인하는지", example = "true")
            boolean isPublic
    ) {
        public static RepositoryResponse from(Room room, RoomMatchInfo roomMatchInfo) {
            return new RepositoryResponse(
                    room.getRepositoryLink(),
                    room.getClassification(),
                    roomMatchInfo.isPublic()
            );
        }
    }

    public record ParticipationResponse(
            @Schema(description = "조회한 유저가 방에 참여하고 있는 상태", example = "PARTICIPATED")
            ParticipationStatus participationStatus,

            @Schema(description = "조회한 유저가 방에 참여한 역할", example = "BOTH")
            MemberRole memberRole,

            @Schema(description = "참여자가 원하는 매칭 인원 수", example = "3")
            int matchingSize
    ) {
        public static ParticipationResponse from(Participation participation) {
            return new ParticipationResponse(
                    participation.getStatus(),
                    participation.getMemberRole(),
                    participation.getMatchingSize()
            );
        }
    }

    public long id(){
        return roomInfoResponse().roomId();
    }
}
