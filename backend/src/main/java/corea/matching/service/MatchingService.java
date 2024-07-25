package corea.matching.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.domain.MatchingStrategy;
import corea.matching.domain.Pair;
import corea.matching.repository.MatchResultRepository;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    private final MatchingStrategy matchingStrategy;
    private final MemberRepository memberRepository;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public void matchMaking(List<Participation> participations, int matchingSize) {
        validateParticipationSize(participations, matchingSize);
        List<Long> memberIds = participations.stream()
                .map(Participation::getMemberId)
                .toList();

        long roomId = participations.get(0).getRoomId();

        List<Pair> results = matchingStrategy.matchPairs(memberIds, matchingSize);

        results.stream()
                .map(pair -> new MatchResult(
                        roomId,
                        memberRepository.findById(pair.getFromMemberId()).orElseThrow(
                                () -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", pair.getFromMemberId()))
                        ),
                        memberRepository.findById(pair.getToMemberId()).orElseThrow(
                                () -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", pair.getToMemberId()))
                        ),
                        null))
                //TODO: prLink 차후 수정
                .forEach(matchResultRepository::save);
    }

    private void validateParticipationSize(List<Participation> participations, int matchingSize) {
        if (participations.size() <= matchingSize) {
            throw new CoreaException(ExceptionType.PARTICIPANT_SIZE_LACK);
        }
    }
}
