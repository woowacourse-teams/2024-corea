package corea.fixture;

import corea.member.domain.Member;
import corea.room.domain.Classification;
import corea.room.domain.Room;
import corea.room.domain.RoomStatus;

import java.time.LocalDateTime;
import java.util.List;

public class RoomFixture {

    public static Room ROOM_DOMAIN(Member member) {
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
                LocalDateTime.now()
                        .plusDays(14),
                Classification.BACKEND,
                RoomStatus.OPENED
        );
    }
}
