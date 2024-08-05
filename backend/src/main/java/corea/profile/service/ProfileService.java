package corea.profile.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReceivedFeedbacks;
import corea.feedback.repository.RevieweeToReviewerFeedbackRepository;
import corea.feedback.repository.ReviewerToRevieweeFeedbackRepository;
import corea.profile.domain.Profile;
import corea.profile.dto.ProfileResponse;
import corea.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private static final int NUMBER_OF_FEEDBACKS_TO_BE_SHOWN_TO_MEMBER = 3;

    private final ProfileRepository profileRepository;
    private final RevieweeToReviewerFeedbackRepository revieweeToReviewerRepository;
    private final ReviewerToRevieweeFeedbackRepository reviewerToRevieweeRepository;

    public ProfileResponse findOne(long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId)));
        List<String> topFeedbackKeywords = findTopFeedbackKeywords(memberId);

        return ProfileResponse.of(profile, topFeedbackKeywords);
    }

    private List<String> findTopFeedbackKeywords(long memberId) {
        List<List<FeedbackKeyword>> reviewerFeedbacks = revieweeToReviewerRepository.findAllKeywordsByReviewerId(memberId);
        List<List<FeedbackKeyword>> revieweeFeedbacks = reviewerToRevieweeRepository.findAllKeywordsByRevieweeId(memberId);
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(reviewerFeedbacks, revieweeFeedbacks);

        return receivedFeedbacks.findTopFeedbackKeywords(NUMBER_OF_FEEDBACKS_TO_BE_SHOWN_TO_MEMBER);
    }
}
