package corea.room.fixture;

import corea.member.domain.Member;
import corea.room.domain.Classification;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;

import java.time.LocalDateTime;

public class RoomFixture {

    public static RoomCreateRequest ROOM_CREATE_REQUEST(Member member) {
        return new RoomCreateRequest(
                "자바 레이싱 카 - TDD",
                "TDD를 배우고 싶은 자 나에게로",
                member,
                "https://github.com/jcoding-play/java-racingcar",
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fgongu.copyright.or.kr%2Fgongu%2Fwrt%2Fwrt%2Fview.do%3FwrtSn%3D13301655%26menuNo%3D200018&psig=AOvVaw1ZSsaFcTZiPv-FFH8i0ejs&ust=1721369458445000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCKD_tIr3r4cDFQAAAAAdAAAAABAE",
                3,
                "TDD",
                15,
                20,
                LocalDateTime.of(2024, 12, 25, 12, 0),
                LocalDateTime.of(2024, 12, 30, 12, 0),
                Classification.BACKEND,
                RoomStatus.OPENED
        );
    }
}
