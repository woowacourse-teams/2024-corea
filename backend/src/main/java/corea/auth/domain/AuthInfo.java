package corea.auth.domain;

import corea.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthInfo {

    private static final long NOT_EXIST_ID = -1L;
    private static final String EMPTY_STRING = "";
    private static final AuthInfo ANONYMOUS = new AuthInfo(NOT_EXIST_ID, EMPTY_STRING, EMPTY_STRING);

    private final long id;
    private final String name;
    private final String email;

    public static AuthInfo from(Member member) {
        return new AuthInfo(member.getId(), member.getUsername(), member.getEmail());
    }

    public static AuthInfo getAnonymous() {
        return ANONYMOUS;
    }
}
