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
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                RoomStatus.OPEN
        );
    }

    public static Room ROOM_DOMAIN_WITH_DEADLINE(Member member, LocalDateTime deadline) {
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
                deadline,
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                RoomStatus.OPEN
        );
    }

    public static Room ROOM_DOMAIN_WITH_CLOSED(Member member) {
        return new Room(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                4,
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                List.of("TDD, 클린코드,자바"),
                17,
                30,
                member,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(14),
                RoomClassification.BACKEND,
                RoomStatus.CLOSE
        );
    }

    public static RoomCreateRequest ROOM_CREATE_REQUEST() {
        return new RoomCreateRequest(
                "자바 레이싱 카 - MVC",
                "MVC 패턴을 아시나요?",
                "https://github.com/example/java-racingcar",
                "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                3,
                List.of("TDD, 클린코드,자바"),
                10,
                LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusDays(2),
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
