package corea.feedback.service;

import corea.feedback.domain.SocialFeedback;
import corea.feedback.domain.SocialFeedbackReader;
import corea.feedback.domain.SocialFeedbackWriter;
import corea.feedback.dto.SocialFeedbackCreateRequest;
import corea.feedback.dto.SocialFeedbackResponse;
import corea.feedback.dto.SocialFeedbackUpdateInput;
import corea.feedback.dto.SocialFeedbackUpdateRequest;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.MatchResultWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialFeedbackService {

    private final SocialFeedbackReader socialFeedbackReader;
    private final SocialFeedbackWriter socialFeedbackWriter;
    private final MatchResultWriter matchResultWriter;
    private final FeedbackMapper feedbackMapper;

    @Transactional
    public SocialFeedbackResponse create(long roomId, long deliverId, SocialFeedbackCreateRequest request) {
        MatchResult matchResult = matchResultWriter.completeSocialFeedback(roomId, deliverId, request.receiverId());

        SocialFeedback feedback = request.toEntity(roomId, matchResult.getReviewee(), matchResult.getReviewer());
        SocialFeedback createdFeedback = socialFeedbackWriter.create(feedback, roomId, deliverId, request.receiverId());

        return SocialFeedbackResponse.from(createdFeedback);
    }

    @Transactional
    public SocialFeedbackResponse update(long feedbackId, long deliverId, SocialFeedbackUpdateRequest request) {
        SocialFeedback socialFeedback = socialFeedbackReader.findById(feedbackId);

        SocialFeedbackUpdateInput input = feedbackMapper.toFeedbackInput(request);
        socialFeedbackWriter.update(socialFeedback, deliverId, input);

        return SocialFeedbackResponse.from(socialFeedback);
    }

    public SocialFeedbackResponse findSocialFeedback(long roomId, long deliverId, String username) {
        SocialFeedback socialFeedback = socialFeedbackReader.findSocialFeedback(roomId, deliverId, username);
        return SocialFeedbackResponse.from(socialFeedback);
    }
}
