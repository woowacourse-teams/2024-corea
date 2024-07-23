package corea.review.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.fixture.MatchResultFixture;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.matching.domain.ReviewStatus;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
class ReviewServiceTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReviewService sut;
    @Autowired
    MatchResultRepository matchResultRepository;

    @Test
    @Transactional
    @DisplayName("리뷰를 완료한다.")
    void review() {
        Member member1 = memberRepository.save(MemberFixture.MEMBER_DOMAIN());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_DOMAIN2());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_MANAGER())));
        MatchResult matchResult = matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), member1, member2));

        sut.review(room.getId(), member1.getId(), member2.getId());
        assertThat(matchResult.getReviewStatus()).isEqualTo(ReviewStatus.COMPLETE);
    }

    @Test
    @DisplayName("리뷰가 이미 존재하면 예외를 발생한다.")
    void review_throw_exception_when_already_exist_matchResult() {
        Member member1 = memberRepository.save(MemberFixture.MEMBER_DOMAIN());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_DOMAIN2());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_MANAGER())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), member1, member2));

        sut.review(room.getId(), member1.getId(), member2.getId());
        assertThatThrownBy(() -> sut.review(room.getId(), member1.getId(), member2.getId()))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @DisplayName("방과 멤버들에 해당하는 매칭결과가 없으면 예외를 발생한다.")
    void review_throw_exception_when_not_exist_room_and_members() {
        assertThatThrownBy(() -> sut.review(-1, -1, -1))
                .isInstanceOf(CoreaException.class);
    }
}
