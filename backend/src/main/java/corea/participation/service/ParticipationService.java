package corea.participation.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationWriter;
import corea.participation.dto.ParticipationRequest;
import corea.participation.dto.ParticipationResponse;
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
    private final ParticipationWriter participationWriter;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ParticipationResponse participate(ParticipationRequest request) {
        validateIdExist(request.roomId(), request.memberId());
        return ParticipationResponse.from(saveParticipation(request));
    }

    private Participation saveParticipation(ParticipationRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
        MemberRole memberRole = MemberRole.from(request.role());
        Room room = getRoom(request.roomId());

        return participationWriter.create(room, member, memberRole, request.matchingSize());
    }

    @Transactional
    public void cancel(long roomId, long memberId) {
        validateMemberExist(memberId);
        participationWriter.delete(roomId, memberId);
    }

    private void validateIdExist(long roomId, long memberId) {
        validateMemberExist(memberId);
        if (participationRepository.existsByRoomIdAndMemberId(roomId, memberId)) {
            throw new CoreaException(ExceptionType.ALREADY_PARTICIPATED_ROOM);
        }
    }

    private void validateMemberExist(long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId));
        }
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }
}
