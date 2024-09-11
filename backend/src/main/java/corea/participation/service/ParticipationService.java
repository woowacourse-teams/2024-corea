package corea.participation.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.dto.ParticipationRequest;
import corea.participation.dto.ParticipationResponse;
import corea.participation.dto.ParticipationsResponse;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return ParticipationResponse.from(saveParticipation(request));
    }

    private Participation saveParticipation(ParticipationRequest request) {
        Room room = getRoom(request.roomId());
        room.participate();
        return participationRepository.save(request.toEntity());
    }

    private void validateIdExist(long roomId, long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId));
        }
        if (participationRepository.existsByRoomIdAndMemberId(roomId, memberId)) {
            throw new CoreaException(ExceptionType.ALREADY_APPLY);
        }
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }

    public ParticipationsResponse getParticipation(long roomId) {
        return new ParticipationsResponse(participationRepository.findAllByRoomId(roomId));
    }
}
