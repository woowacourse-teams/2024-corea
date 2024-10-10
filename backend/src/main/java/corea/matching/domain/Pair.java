package corea.matching.domain;

import corea.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Pair {

    private final Member deliver;
    private final Member receiver;

    public String getReceiverGithubId() {
        return receiver.getGithubUserId();
    }

    public boolean hasReviewer(Member reviewer) {
        return deliver.equals(reviewer);
    }

    public boolean hasReviewee(Member reviewee) {
        return receiver.equals(reviewee);
    }
}
