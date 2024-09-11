package corea.member.service;

import config.ServiceTest;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.dto.ProfileResponse;
import corea.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ServiceTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DevelopFeedbackRepository reviewerToRevieweeRepository;

    @Test
    @DisplayName("마이 페이지에 보여질 정보를 찾을 수 있다.")
    void findOne() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joyson = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());

        List<FeedbackKeyword> feedbackKeywords = getFeedbackKeywords();
        DevelopFeedback developFeedback = new DevelopFeedback(1, joyson, pororo, 5, feedbackKeywords, "", 1);
        reviewerToRevieweeRepository.save(developFeedback);

        ProfileResponse response = memberService.findProfileInfoById(pororo.getId());

        assertSoftly(softly -> {
            softly.assertThat(response.nickname()).isEqualTo("pororo");
            softly.assertThat(response.feedbackKeywords()).hasSize(3);
        });
    }

    private List<FeedbackKeyword> getFeedbackKeywords() {
        return List.of(
                FeedbackKeyword.MAKE_CODE_FOR_THE_PURPOSE,
                FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE,
                FeedbackKeyword.RESPONSE_FAST
        );
    }
}
