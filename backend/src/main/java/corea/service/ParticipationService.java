package corea.service;

import corea.domain.Participation;
import corea.dto.ParticipationRequest;
import corea.dto.ParticipationResponse;
import corea.repository.MemberRepository;
import corea.repository.ParticipationRepository;
import corea.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public ParticipationResponse participate(final ParticipationRequest request) {
        validateIdExist(request.roomId(), request.memberId());

        final Participation participation = participationRepository.save(request.toEntity());
        return ParticipationResponse.from(participation);
    }

    private void validateIdExist(final long roomId, final long memberId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException(String.format("해당 Id의 방이 없어 참여할 수 없습니다. 입력된 방 Id=%d", roomId));
        }
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException(String.format("해당 Id의 멤버가 없어 방에 참여할 수 없습니다. 입력된 멤버 Id=%d", memberId));
        }
    }
}
