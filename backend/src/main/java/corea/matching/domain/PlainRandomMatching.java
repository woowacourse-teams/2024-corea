package corea.matching.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PlainRandomMatching implements MatchingStrategy {

    @Override
    public List<Pair> matchPairs(List<Long> memberIds, int matchingSize) {
        List<Long> shuffledMemberIds = new ArrayList<>(memberIds);
        Collections.shuffle(shuffledMemberIds);

        return match(shuffledMemberIds, matchingSize);
    }

    private List<Pair> match(List<Long> shuffledMemberIds, int matchingSize) {
        List<Pair> reviewerResult = new ArrayList<>();
        for (int i = 0; i < shuffledMemberIds.size(); i++) {
            for (int j = 1; j <= matchingSize; j++) {
                reviewerResult.add(new Pair(shuffledMemberIds.get(i), shuffledMemberIds.get((i + j) % shuffledMemberIds.size())));
            }
        }
        return reviewerResult;
    }
}
