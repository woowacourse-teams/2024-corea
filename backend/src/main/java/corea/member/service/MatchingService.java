package corea.member.service;

import corea.member.domain.Matching;
import corea.member.entity.MatchedGroup;
import corea.domain.Member;
import corea.member.repository.MatchedGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MatchingService {

    private final MatchedGroupRepository matchedGroupRepository;
    private final Matching matching;

    public void matchMaking(final List<Member> members, final int matchingSize) {
        final Map<Long, List<Long>> results = matching.matchGroup(members, matchingSize);
        results.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(memberId -> new MatchedGroup(entry.getKey(), memberId)))
                .forEach(matchedGroupRepository::save);
    }
}
