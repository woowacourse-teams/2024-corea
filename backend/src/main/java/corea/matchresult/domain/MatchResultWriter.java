package corea.matchresult.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class MatchResultWriter {

    public void reviewComplete(MatchResult matchResult, String prLink) {
        matchResult.reviewComplete();
        matchResult.updateReviewLink(prLink);
    }
}
