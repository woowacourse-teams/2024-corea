package corea.room.repository;

import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("자신이 참여하지 않고, 계속 모집 중인 방들을 모집 마감일이 임박한 순으로 조회할 수 있다.")
    void findAllByMemberAndClassificationAndStatus() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(joysun, LocalDateTime.now().plusDays(3)));

        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Page<Room> roomPage = roomRepository.findAllByMemberAndClassificationAndStatus(movin.getId(), RoomClassification.BACKEND, RoomStatus.OPEN, PageRequest.of(0, 8));

        List<String> managerNames = getManagerNames(roomPage.getContent());
        assertThat(managerNames).containsExactly("조경찬", "이영수");
    }

    @Test
    @DisplayName("분야와 상관 없이 자신이 참여하지 않고, 계속 모집 중인 방들을 모집 마감일이 임박한 순으로 조회할 수 있다.")
    void findAllByMemberAndStatus() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        roomRepository.save(RoomFixture.ROOM_DOMAIN(joysun, LocalDateTime.now().plusDays(3)));

        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Page<Room> roomPage = roomRepository.findAllByMemberAndStatus(movin.getId(), RoomStatus.OPEN, PageRequest.of(0, 8));

        List<String> managerNames = getManagerNames(roomPage.getContent());
        assertThat(managerNames).containsExactly("조경찬", "이영수");
    }

    @Test
    @DisplayName("리뷰 마감일이 임박한 순으로 방 리스트를 반환한다.")
    void findAllByIdInOrderByReviewDeadlineAsc() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Room pororoRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusDays(2)));
        Room joysunRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(joysun, LocalDateTime.now().plusDays(3)));

        List<Room> rooms = roomRepository.findAllByIdInOrderByReviewDeadlineAsc(List.of(pororoRoom.getId(), joysunRoom.getId()));

        List<String> managerNames = getManagerNames(rooms);
        assertThat(managerNames).containsExactly("조경찬", "이영수");
    }

    private List<String> getManagerNames(List<Room> rooms) {
        return rooms.stream()
                .map(Room::getManagerName)
                .toList();
    }
}
