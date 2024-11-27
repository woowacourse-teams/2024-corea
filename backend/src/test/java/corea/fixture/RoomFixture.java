package corea.fixture;

import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomDeadline;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomRequest;
import corea.room.dto.RoomUpdateRequest;

import java.time.LocalDateTime;
import java.util.List;

//@formatter:off
public class RoomFixture {

    public static Room ROOM_DOMAIN(Member member) {
        return ROOM_DOMAIN(member, LocalDateTime.now().plusHours(1), RoomStatus.OPEN);
    }

    public static Room ROOM_DOMAIN(Member member, LocalDateTime recruitmentDeadline) {
        return ROOM_DOMAIN(member, recruitmentDeadline, RoomStatus.OPEN);
    }

    public static Room ROOM_DOMAIN_REVIEW_DEADLINE(Member member,LocalDateTime reviewDeadline) {
        return ROOM_DOMAIN(member, LocalDateTime.now().plusHours(1), reviewDeadline, RoomStatus.OPEN);
    }

    public static Room ROOM_DOMAIN_WITH_CLOSED(Member member) {
        return ROOM_DOMAIN(member, LocalDateTime.now().plusHours(1), RoomStatus.CLOSE);
    }

    public static Room ROOM_DOMAIN_WITH_PROGRESS(Member member) {
        return ROOM_DOMAIN(member, LocalDateTime.now().plusHours(1), RoomStatus.PROGRESS);
    }

    public static Room ROOM_DOMAIN(Member member, LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline, RoomStatus status) {
        return new Room(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                1,
                17,
                30,
                member,
                recruitmentDeadline,
                reviewDeadline,
                RoomClassification.BACKEND,
                status
        );
    }

    public static Room ROOM_DOMAIN(Member member, LocalDateTime recruitmentDeadline, RoomStatus status) {
        return new Room(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                1,
                17,
                30,
                member,
                recruitmentDeadline,
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                status
        );
    }

    public static Room ROOM_DOMAIN_WITH_DIFFERENT_TITLE(Member member, LocalDateTime recruitmentDeadline, RoomStatus status) {
        return new Room(
                "코틀린 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                1,
                17,
                30,
                member,
                recruitmentDeadline,
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                status
        );
    }

    public static Room ROOM_DOMAIN_WITH_CLASSIFICATION(Member member, LocalDateTime recruitmentDeadline, RoomClassification classification) {
        return new Room(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                1,
                17,
                30,
                member,
                recruitmentDeadline,
                LocalDateTime.now().plusDays(14),
                classification,
                RoomStatus.OPEN
        );
    }
    public static Room ROOM_DOMAIN(String repositoryLink,Member member){
        return new Room(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                repositoryLink,
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                1,
                17,
                30,
                member,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                RoomStatus.OPEN
        );
    }

    public static Room ROOM_DOMAIN(Long id, Member member) {
        return new Room(
                id,
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                1,
                17,
                30,
                member,
                new RoomDeadline(
                        LocalDateTime.now().plusHours(1),
                        LocalDateTime.now().plusDays(14)
                ),
                RoomClassification.BACKEND,
                RoomStatus.OPEN
        );
    }
    public static RoomUpdateRequest ROOM_UPDATE_REQUEST(long roomId){
        return new RoomUpdateRequest(
                roomId,
                "update Room",
                "update Content",
                "https://github.com/youngsu5582/github-api-test",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                2,
                List.of("TDD, 클린코드, 자바"),
                10,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND
        );
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST() {
        return ROOM_CREATE_REQUEST(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(4));
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST_WITH_RECRUITMENT_DEADLINE(LocalDateTime recruitmentDeadline) {
        return ROOM_CREATE_REQUEST(recruitmentDeadline, LocalDateTime.now().plusDays(4));
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(LocalDateTime reviewDeadline) {
        return ROOM_CREATE_REQUEST(LocalDateTime.now().plusHours(2), reviewDeadline);
    }

    public static RoomRequest ROOM_REQUEST() {
        return new RoomRequest(
            new RoomRequest.RoomInfoRequest(
                "Room", "Content", "https://gongu.copyright.or.kr/gongu/wrt",
                    3,List.of("JAVA","TDD"),200
            ),
            new RoomRequest.DeadlineRequest(
                    LocalDateTime.now().plusHours(2),
                    LocalDateTime.now().plusHours(4)
            ),
            new RoomRequest.RepositoryRequest(
                    "https://github.com/example/java-racingcar",
                    RoomClassification.BACKEND,
                    true
            ),
            new RoomRequest.ManagerParticipationRequest(MemberRole.REVIEWER,3)
        );
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        return new RoomCreateRequest(
                "create Room",
                "create Content",
                "https://github.com/youngsu5582/github-api-test",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                2,
                List.of("TDD, 클린코드, 자바"),
                10,
                recruitmentDeadline,
                reviewDeadline,
                RoomClassification.ALL,
                true
        );
    }

    public static Room ROOM_PULL_REQUEST(Member member) {
        return new Room(
                "Test Room",
                "Test Content",
                3,
                "https://github.com/youngsu5582/github-api-test",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("test1", "test2"),
                0,
                0,
                100,
                member,
                LocalDateTime.now().plusSeconds(100),
                LocalDateTime.now().plusDays(1),
                RoomClassification.BACKEND, RoomStatus.OPEN);
    }
}
//@formatter:on
