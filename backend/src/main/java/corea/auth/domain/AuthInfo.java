package corea.auth.domain;

import corea.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class AuthInfo {

    private final Long id;

    private final String name;

    private final String email;

    public static AuthInfo from(Member member){
        return new AuthInfo(member.getId(), member.getUserName(), member.getEmail());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthInfo authInfo)) return false;
        return Objects.equals(id, authInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
