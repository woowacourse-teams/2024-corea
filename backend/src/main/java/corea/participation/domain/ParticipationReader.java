package corea.participation.domain;

import corea.member.domain.MemberRole;
import corea.participation.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipationReader {

    private final ParticipationRepository participationRepository;

    public MemberRole findMemberRole(long roomId, long memberId) {
        return participationRepository.findByRoomIdAndMemberId(roomId, memberId)
                .map(Participation::getMemberRole)
                .orElse(MemberRole.NONE);
    }
}
