package corea.matching.domain;

import corea.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Pair {

    private final Member deliver;
    private final Member receiver;

    public String getToMemberGithubId() {
        return receiver.getGithubUserId();
    }
}
