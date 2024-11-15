package corea.participation.domain;

import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.participation.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipationReader {

    private final ParticipationRepository participationRepository;

    public MemberRole findMemberRole(long roomId, long memberId) {
        return participationRepository.findByRoomIdAndMemberId(roomId, memberId)
                .map(Participation::getMemberRole)
                .orElse(MemberRole.NONE);
    }

    public List<Long> findRevieweeIdsByRoomId(long roomId) {
        return participationRepository.findAllByRoomId(roomId)
                .stream()
                .filter(Participation::isNotReviewer)
                .map(Participation::getMember)
                .map(Member::getId)
                .toList();
    }

    public List<Participation> findAllByRoomId(long roomId) {
        return participationRepository.findAllByRoomId(roomId);
    }
}
