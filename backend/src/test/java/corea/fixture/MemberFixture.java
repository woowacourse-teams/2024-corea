package corea.fixture;

import corea.member.domain.Member;

public class MemberFixture {

    public static Member MEMBER_YOUNGSU() {
        return new Member(
                "youngsu5582",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "youngsu5582@gmail.com",
                false
        );
    }

    public static Member MEMBER_PORORO() {
        return new Member(
                "pororo",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "jcoding-play@gmail.com",
                false
        );
    }

    public static Member MEMBER_ASH() {
        return new Member(
                "ashsty",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "박민아",
                null,
                false
        );
    }

    public static Member MEMBER_MUBIN() {
        return new Member(
                "hjk0761",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "김현중",
                null,
                false
        );
    }

    public static Member MEMBER_ROOM_MANAGER_JOYSON() {
        return new Member(
                "joyson5582",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "joyson5582@gmail.com",
                false
        );
    }
}
