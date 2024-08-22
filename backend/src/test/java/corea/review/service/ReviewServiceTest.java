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
import corea.member.domain.Profile;
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
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Test
    @Transactional
    @DisplayName("리뷰를 완료한다.")
    void review() {
        Member member1 = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member member2 = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        MatchResult matchResult = matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), member1, member2));

        reviewService.review(room.getId(), member1.getId(), member2.getId());

        assertThat(matchResult.getReviewStatus()).isEqualTo(ReviewStatus.COMPLETE);
    }

    @Test
    @DisplayName("방과 멤버들에 해당하는 매칭결과가 없으면 예외를 발생한다.")
    void review_throw_exception_when_not_exist_room_and_members() {
        assertThatThrownBy(() -> reviewService.review(-1, -1, -1))
                .isInstanceOf(CoreaException.class);
    }

    @Test
    @Transactional
    @DisplayName("리뷰를 작성하면 리뷰 작성한 개수가 증가한다.")
    void increaseReviewCount1() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        reviewService.review(room.getId(), reviewer.getId(), reviewee.getId());

        Profile profile = reviewer.getProfile();
        assertThat(profile.getDeliverCount()).isEqualTo(1);
        assertThat(profile.getReceiveCount()).isZero();
    }

    @Test
    @Transactional
    @DisplayName("리뷰를 받으면 리뷰 받은 개수가 증가한다.")
    void increaseReviewCount2() {
        Member reviewer = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member reviewee = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON())));
        matchResultRepository.save(MatchResultFixture.MATCH_RESULT_DOMAIN(room.getId(), reviewer, reviewee));

        reviewService.review(room.getId(), reviewer.getId(), reviewee.getId());

        Profile profile = reviewee.getProfile();
        assertThat(profile.getDeliverCount()).isZero();
        assertThat(profile.getReceiveCount()).isEqualTo(1);
    }
}
