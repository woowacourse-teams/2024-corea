package corea.matching.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.Participation;
import corea.matching.dto.ParticipationRequest;
import corea.matching.dto.ParticipationResponse;
import corea.member.repository.MemberRepository;
import corea.matching.repository.ParticipationRepository;
import corea.room.repository.RoomRepository;
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
            throw new CoreaException(ExceptionType.NOT_FOUND_ERROR,String.format("%d에 해당하는 방이 없습니다.",roomId));
        }
        if (!memberRepository.existsById(memberId)) {
            throw new CoreaException(ExceptionType.NOT_FOUND_ERROR,String.format("%d에 해당하는 멤버가 없습니다.",memberId));
        }
        if(participationRepository.existsByRoomIdAndMemberId(roomId, memberId)) {
            throw new CoreaException(ExceptionType.ALREADY_APPLY);
        }
    }
}
