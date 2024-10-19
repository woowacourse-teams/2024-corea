package corea.feedback.service;

import corea.feedback.domain.SocialFeedback;
import corea.feedback.domain.SocialFeedbackReader;
import corea.feedback.domain.SocialFeedbackWriter;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.feedback.dto.SocialFeedbackResponse;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.MatchResultWriter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialFeedbackService {

    private static final Logger log = LogManager.getLogger(SocialFeedbackService.class);

    private final SocialFeedbackReader socialFeedbackReader;
    private final SocialFeedbackWriter socialFeedbackWriter;
    private final MatchResultWriter matchResultWriter;

    @Transactional
    public SocialFeedbackResponse create(long roomId, long deliverId, SocialFeedbackRequest request) {
        MatchResult matchResult = matchResultWriter.completeSocialFeedback(roomId, deliverId, request.receiverId());

        SocialFeedback feedback = request.toEntity(roomId, matchResult.getReviewee(), matchResult.getReviewer());
        SocialFeedback createdFeedback = socialFeedbackWriter.create(feedback, roomId, deliverId, request.receiverId());

        return SocialFeedbackResponse.from(createdFeedback);
    }

    @Transactional
    public SocialFeedbackResponse update(long feedbackId, long deliverId, SocialFeedbackRequest request) {
        log.info("소설 피드백 업데이트[작성자({}), 피드백 ID({}), 요청값({})", deliverId, feedbackId, request);

        SocialFeedback socialFeedback = socialFeedbackReader.findById(feedbackId);

        updateFeedback(socialFeedback, request);

        return SocialFeedbackResponse.from(socialFeedback);
    }

    private void updateFeedback(SocialFeedback feedback, SocialFeedbackRequest request) {
        feedback.update(
                request.evaluationPoint(),
                request.feedbackKeywords(),
                request.feedbackText()
        );
    }

    public SocialFeedbackResponse findSocialFeedback(long roomId, long deliverId, String username) {
        SocialFeedback socialFeedback = socialFeedbackReader.findSocialFeedback(roomId, deliverId, username);
        return SocialFeedbackResponse.from(socialFeedback);
    }
}
