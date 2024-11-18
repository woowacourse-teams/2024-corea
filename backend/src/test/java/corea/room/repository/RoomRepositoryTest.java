package corea.room.repository;

import config.RepositoryTest;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Test
    @DisplayName("선택한 분야와 일치하는 방들을 조회할 수 있다.")
    void findAllByClassificationAndStatus() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLASSIFICATION(pororo, LocalDateTime.now().plusDays(2), RoomClassification.ANDROID));
        roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_CLASSIFICATION(joyson, LocalDateTime.now().plusDays(3), RoomClassification.BACKEND));

        Page<Room> roomPage = roomRepository.findAllByClassificationAndStatus(RoomClassification.BACKEND, RoomStatus.OPEN, PageRequest.of(0, 8));

        List<String> managerNames = getManagerNames(roomPage.getContent());
        assertThat(managerNames).containsExactly("youngsu5582");
    }

    @Test
    @DisplayName("모집 중인 방들을 조회할 때 자신이 참여한 방도 포함하여 조회한다.")
    void findAllByMemberAndStatus_participated() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(joyson, LocalDateTime.now().plusDays(3)));

        participationRepository.save(new Participation(pororoRoom, pororo, MemberRole.REVIEWER, ParticipationStatus.MANAGER,pororoRoom.getMatchingSize()));
        Page<Room> roomPage = roomRepository.findAllByStatus(RoomStatus.OPEN, PageRequest.of(0, 8));

        List<String> managerNames = getManagerNames(roomPage.getContent());
        assertThat(managerNames).containsExactly("pororo", "youngsu5582");
    }

    @Test
    @DisplayName("모집 중인 방들을 모집 마감일이 임박한 순으로 조회할 수 있다.")
    void findAllByMemberAndStatus_notParticipated() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(joyson, LocalDateTime.now().plusDays(3)));

        Page<Room> roomPage = roomRepository.findAllByStatus(RoomStatus.OPEN, PageRequest.of(0, 8));

        List<String> managerNames = getManagerNames(roomPage.getContent());
        assertThat(managerNames).containsExactly("pororo", "youngsu5582");
    }

    @Test
    @DisplayName("리뷰 마감일이 임박한 순으로 방 리스트를 반환한다.")
    void findAllByIdInOrderByRoomDeadline_ReviewDeadlineAsc() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joyson = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room joysonRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(joyson, LocalDateTime.now().plusDays(3)));

        List<Room> rooms = roomRepository.findAllByIdInOrderByRoomDeadline_ReviewDeadlineAsc(List.of(pororoRoom.getId(), joysonRoom.getId()));

        List<String> managerNames = getManagerNames(rooms);
        assertThat(managerNames).containsExactly("pororo", "youngsu5582");
    }

    private List<String> getManagerNames(List<Room> rooms) {
        return rooms.stream()
                .map(Room::getManagerName)
                .toList();
    }
}
