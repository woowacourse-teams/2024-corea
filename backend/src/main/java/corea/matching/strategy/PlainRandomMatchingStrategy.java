package corea.matching.strategy;

import corea.matching.domain.Pair;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlainRandomMatchingStrategy implements MatchingStrategy {

    private final MemberRepository memberRepository;

    @Override
    public List<Pair> matchPairs(List<Participation> participations, int matchingSize) {
        List<Member> members = participations.stream()
                .map(participation -> memberRepository.findById(participation.getMembersId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        Collections.shuffle(members);

        return match(members, matchingSize);
    }

    private List<Pair> match(List<Member> members, int matchingSize) {
        List<Pair> reviewerResult = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            for (int j = 1; j <= matchingSize; j++) {
                reviewerResult.add(new Pair(members.get(i), members.get((i + j) % members.size())));
            }
        }
        return reviewerResult;
    }
}
