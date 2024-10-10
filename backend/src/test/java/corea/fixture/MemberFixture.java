package corea.fixture;

import corea.member.domain.Member;
import corea.member.domain.Profile;

import java.util.List;
import java.util.stream.IntStream;

public class MemberFixture {

    public static Member MEMBER_YOUNGSU() {
        return new Member(
                "youngsu5582",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "이영수",
                "youngsu5582@gmail.com",
                false,
                "98307410"
        );
    }

    public static Member MEMBER_PORORO() {
        return new Member(
                "pororo",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "조경찬",
                "jcoding-play@gmail.com",
                false,
                "119468757"
        );
    }

    public static Member MEMBER_ASH() {
        return new Member(
                "ashsty",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "박민아",
                "ashsty@gmail.com",
                false,
                "77227961"
        );
    }

    public static Member MEMBER_MOVIN() {
        return new Member(
                "hjk0761",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "김현중",
                "hjk0761@gmail.com",
                false,
                "80106238"
        );
    }

    public static Member MEMBER_ROOM_MANAGER_JOYSON() {
        return new Member(
                "joyson5582",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "조이썬",
                "joyson5582@gmail.com",
                false,
                "99112400"
        );
    }

    public static Member MEMBER_TENTEN() {
        return new Member(
                "chlwlstlf",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "최진실",
                "tenten@gmail.com",
                false,
                "63334368"
        );
    }

    public static Member MEMBER_CHOCO() {
        return new Member(
                "choco",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "강다빈",
                "choco@gmail.com",
                false,
                "70834044"
        );
    }

    public static Member MEMBER_DARR() {
        return new Member(
                "darr",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                "이상엽",
                "darr@gmail.com",
                false,
                "71641127"
        );
    }

    public static Member MEMBER_PORORO_GITHUB() {
        return new Member(
                "jcoding-play",
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "jcoding-play@gmail.com",
                false,
                "119468757"
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
        return List.of(MEMBER_PORORO(), MEMBER_ASH(), MEMBER_YOUNGSU(), MEMBER_CHOCO(), MEMBER_MOVIN(), MEMBER_TENTEN());
    }

    public static List<Member> CREATE_MEMBERS(int index) {
        return IntStream.range(0,index).mapToObj(idx->new Member(
                "name : "+(idx+10),
                "https://avatars.githubusercontent.com/u/98307410?v=4",
                null,
                "jcoding-play@gmail.com",
                false,
                "119468757"
        )).toList();
    }
}
