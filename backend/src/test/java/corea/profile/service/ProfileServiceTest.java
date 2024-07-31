package corea.profile.service;

import config.ServiceTest;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReviewerToReviewee;
import corea.feedback.repository.ReviewerToRevieweeRepository;
import corea.fixture.MemberFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.profile.domain.Profile;
import corea.profile.dto.ProfileResponse;
import corea.profile.repository.ProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ServiceTest
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewerToRevieweeRepository reviewerToRevieweeRepository;

    @Test
    @DisplayName("마이 페이지에 보여질 정보를 찾을 수 있다.")
    void findOne() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member joyson = memberRepository.save(MemberFixture.MEMBER_JOYSON());

        List<FeedbackKeyword> feedbackKeywords = getFeedbackKeywords();
        ReviewerToReviewee reviewerToReviewee = new ReviewerToReviewee(pororo.getId(), joyson.getId(), 5, feedbackKeywords, null, 1);
        reviewerToRevieweeRepository.save(reviewerToReviewee);

        Profile profile = new Profile(pororo, 1, 1, 0, 5);
        profileRepository.save(profile);

        ProfileResponse response = profileService.findOne(pororo.getId());

        assertSoftly(softly -> {
            softly.assertThat(response.nickname()).isEqualTo("pororo");
            softly.assertThat(response.feedbackKeywords()).hasSize(3);
        });
    }

    private List<FeedbackKeyword> getFeedbackKeywords() {
        return List.of(
                FeedbackKeyword.MAKE_CODE_FOR_THE_PURPOSE,
                FeedbackKeyword.EASY_TO_UNDERSTAND_THE_CODE,
                FeedbackKeyword.RESPONSE_WAS_FAST,
                FeedbackKeyword.HARD_TO_UNDERSTAND_THE_CODE
        );
    }
}
