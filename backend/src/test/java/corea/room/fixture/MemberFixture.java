package corea.room.fixture;

import corea.member.domain.Member;

public class MemberFixture {

    public static Member MEMBER_PORORO() {
        return new Member("jcoding-play", null, "조경찬", "namejgc@naver.com", true, 5f);
    }

    public static Member MEMBER_ASH() {
        return new Member("ashsty", null, "박민아", null, false, 1.5f);
    }
}
