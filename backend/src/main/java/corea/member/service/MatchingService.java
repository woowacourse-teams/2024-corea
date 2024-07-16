package corea.member.service;

import corea.domain.Participation;
import corea.member.domain.Matching;
import corea.member.entity.MatchedGroup;
import corea.member.repository.MatchedGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MatchingService {

    private final MatchedGroupRepository matchedGroupRepository;
    private final Matching matching;

    public void matchMaking(final List<Participation> participations, final int matchingSize) {
        final ArrayList<Long> memberIds = new ArrayList<>(participations.stream()
                .map(Participation::getMemberId)
                .toList()
        );

        final Map<Long, List<Long>> results = matching.matchGroup(memberIds, matchingSize);
        results.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue()
                        .stream()
                        .map(memberId -> new MatchedGroup(entry.getKey(), memberId)))
                .forEach(matchedGroupRepository::save);
    }
}
