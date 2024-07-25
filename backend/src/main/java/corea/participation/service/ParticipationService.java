package corea.participation.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.dto.ParticipationRequest;
import corea.participation.dto.ParticipationResponse;
import corea.participation.dto.ParticipationsResponse;
import corea.participation.repository.ParticipationRepository;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ParticipationResponse participate(ParticipationRequest request) {
        validateIdExist(request.roomId(), request.memberId());

        final Participation participation = participationRepository.save(request.toEntity());
        return ParticipationResponse.from(participation);
    }

    private void validateIdExist(long roomId, long memberId) {
        if (!roomRepository.existsById(roomId)) {
            throw new CoreaException(ExceptionType.NOT_FOUND_ERROR, String.format("%d에 해당하는 방이 없습니다.", roomId));
        }
        if (!memberRepository.existsById(memberId)) {
            throw new CoreaException(ExceptionType.NOT_FOUND_ERROR, String.format("%d에 해당하는 멤버가 없습니다.", memberId));
        }
        if (participationRepository.existsByRoomIdAndMemberId(roomId, memberId)) {
            throw new CoreaException(ExceptionType.ALREADY_APPLY);
        }
    }

    public ParticipationsResponse getParticipation(long roomId) {
        return new ParticipationsResponse(participationRepository.findAllByRoomId(roomId));
    }
}
