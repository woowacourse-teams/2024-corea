package corea.fixture;

import corea.member.domain.Member;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;

import java.time.LocalDateTime;
import java.util.List;

public class RoomFixture {

    public static Room ROOM_DOMAIN(Member member) {
        return ROOM_DOMAIN(member, LocalDateTime.now(), RoomStatus.OPEN);
    }

    public static Room ROOM_DOMAIN(Member member, LocalDateTime recruitmentDeadline) {
        return ROOM_DOMAIN(member, recruitmentDeadline, RoomStatus.OPEN);
    }

    public static Room ROOM_DOMAIN_WITH_CLOSED(Member member) {
        return ROOM_DOMAIN(member, LocalDateTime.now(), RoomStatus.CLOSE);
    }

    public static Room ROOM_DOMAIN_WITH_PROGRESS(Member member) {
        return ROOM_DOMAIN(member, LocalDateTime.now(), RoomStatus.PROGRESS);
    }

    public static Room ROOM_DOMAIN(Member member, LocalDateTime recruitmentDeadline, RoomStatus status) {
        return new Room(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                2,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                17,
                30,
                member,
                recruitmentDeadline,
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                status
        );
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST() {
        return ROOM_CREATE_REQUEST(LocalDateTime.now().plusHours(2), LocalDateTime.now().plusDays(2));
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST_WITH_RECRUITMENT_DEADLINE(LocalDateTime recruitmentDeadline) {
        return ROOM_CREATE_REQUEST(recruitmentDeadline, LocalDateTime.now().plusDays(2));
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST_WITH_REVIEW_DEADLINE(LocalDateTime reviewDeadline) {
        return ROOM_CREATE_REQUEST(LocalDateTime.now().plusHours(2), reviewDeadline);
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST(LocalDateTime recruitmentDeadline, LocalDateTime reviewDeadline) {
        return new RoomCreateRequest(
                "Test Room",
                "Test Content",
                "https://github.com/youngsu5582/github-api-test",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                2,
                List.of("TDD, 클린코드, 자바"),
                10,
                recruitmentDeadline,
                reviewDeadline,
                RoomClassification.ALL
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
                100,
                member,
                LocalDateTime.now().plusSeconds(100),
                LocalDateTime.now().plusDays(1),
                RoomClassification.BACKEND,
                RoomStatus.OPEN);
    }
}
