package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlainRandomMatching implements MatchingStrategy {

    private final MemberRepository memberRepository;

    @Override
    public List<Pair> matchPairs(List<Participation> participations, int matchingSize) {
        validateParticipationSize(participations, matchingSize);

        List<Member> members = new ArrayList<>(participations.stream()
                .map(participation -> memberRepository.findById(participation.getMemberId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
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

    private void validateParticipationSize(List<Participation> participations, int matchingSize) {
        if (participations.size() <= matchingSize) {
            throw new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK);
        }
    }
}
