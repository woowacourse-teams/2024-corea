package corea.participation.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class ParticipationWriter {

    private final ParticipationRepository participationRepository;

    public Participation create(Room room, Member member, MemberRole memberRole,ParticipationStatus participationStatus) {

        return create(room, member, memberRole,participationStatus, room.getMatchingSize());
    }

    public Participation create(Room room, Member member, MemberRole memberRole, int matchingSize) {
        return create(room, member, memberRole,memberRole.getParticipationStatus(),matchingSize);
    }

    private Participation create(Room room, Member member, MemberRole memberRole, ParticipationStatus participationStatus, int matchingSize) {
        Participation participation = participationRepository.save(new Participation(room, member, memberRole, participationStatus, matchingSize));
        participation.participate();
        createLog(participation);
        return participation;
    }

    // TODO 객체 두개를 넣어서 삭제하는 방향으로 변경 해야합니다.
    // 현재, 로직상 cancel 호출 시, 의도하지 않는 room 조회문 발생
    public void delete(long roomId, long memberId) {
        Participation participation = participationRepository.findByRoomIdAndMemberId(roomId, memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_ALREADY_APPLY));
        participation.cancel();
        deleteLog(participation);
        participationRepository.delete(participation);
    }

    private static void createLog(Participation participation) {
        log.info("방에 참가했습니다. id={}, 방 id={}, 참가한 사용자 id={}, 역할={}, 원하는 매칭 인원={}", participation.getId(), participation.getRoomsId(), participation.getMembersId(), participation.getMemberRole(), participation.getMatchingSize());
    }

    private static void deleteLog(Participation participation) {
        log.info("참여를 취소했습니다. 방 id={}, 참가한 사용자 id={}", participation.getRoomsId(), participation.getMembersId());
    }
}
