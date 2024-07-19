package corea.member.service;

import corea.matching.domain.Participation;
import corea.matching.dto.MatchedGroupResponse;
import corea.matching.repository.MatchedGroupRepository;
import corea.matching.repository.ParticipationRepository;
import corea.matching.service.MatchingService;
import corea.member.domain.Member;
import corea.member.entity.MatchedGroup;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchingService matchingService;
    private final ParticipationRepository participationRepository;
    private final MatchedGroupRepository matchedGroupRepository;
    private final MemberRepository memberRepository;

    public void match(final long roomId) {
        final List<Participation> participations = participationRepository.findAllByRoomId(roomId);

        matchingService.matchMaking(participations, 2);
    }

    public MatchedGroupResponse getMatchedGroup(final String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s는 없는 이메일입니다.", email)));

        final MatchedGroup matchedGroup = matchedGroupRepository.findOneByMemberId(member.getId())
                .orElseThrow(IllegalArgumentException::new);

        final List<Long> memberIds = matchedGroupRepository.findByGroupId(matchedGroup.getGroupId())
                .stream()
                .filter(matching -> !matching.isEqualMember(member.getId()))
                .map(MatchedGroup::getMemberId)
                .toList();


        final List<String> memberEmails =
                memberIds.stream()
                        .map(id -> memberRepository.findById(id)
                                .orElse(null))
                        .map(Member::getEmail)
                        .toList();

        return new MatchedGroupResponse(memberEmails);
    }
}
