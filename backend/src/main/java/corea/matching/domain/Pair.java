package corea.matching.domain;

import corea.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Pair {

    private final Member fromMember;
    private final Member toMember;

    public String getToMemberGithubId(){
        return toMember.getGithubUserId();
    }
}
