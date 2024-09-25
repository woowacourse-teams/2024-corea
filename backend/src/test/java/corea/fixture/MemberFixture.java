package corea.fixture;

import corea.member.domain.Member;
import corea.member.domain.Profile;

import java.util.List;

public class MemberFixture {

    public static Member MEMBER_YOUNGSU() {
        return new Member(
                "youngsu5582",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "youngsu5582@gmail.com",
                false,
                "98307410"
        );
    }

    public static Member MEMBER_PORORO() {
        return new Member(
                "pororo",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "jcoding-play@gmail.com",
                false,
                "119468757"
        );
    }

    public static Member MEMBER_ASH() {
        return new Member(
                "ashsty",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "ashsty@gmail.com",
                false,
                "77227961"
        );
    }

    public static Member MEMBER_MUBIN() {
        return new Member(
                "hjk0761",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "hjk0761@gmail.com",
                false,
                "80106238"
        );
    }

    public static Member MEMBER_ROOM_MANAGER_JOYSON() {
        return new Member(
                "joyson5582",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "joyson5582@gmail.com",
                false,
                "99112400"
        );
    }

    public static Member MEMBER_TENTEN() {
        return new Member(
                "chlwlstlf",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "tenten@gmail.com",
                false,
                "63334368"
        );
    }

    public static Member MEMBER_CHOCO() {
        return new Member(
                "choco",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "choco@gmail.com",
                false,
                "70834044"
        );
    }

    public static Member MEMBER_PORORO(Profile profile) {
        return new Member(
                "pororo",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "jcoding-play@gmail.com",
                false,
                "119468757",
                profile
        );
    }

    public static List<Member> SEVEN_MEMBERS() {
        return List.of(MEMBER_PORORO(), MEMBER_ASH(), MEMBER_YOUNGSU(), MEMBER_CHOCO(), MEMBER_MUBIN(), MEMBER_TENTEN());
    }
}
