package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.domain.ReviewStatus;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutomaticUpdateExecutor {

    private final RoomRepository roomRepository;
    private final MatchResultRepository matchResultRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;
    private final AutomaticUpdateRepository automaticUpdateRepository;

    @Async
    @Transactional
    public void execute(long roomId) {
        Room room = getRoom(roomId);
        room.updateStatusToClose();

        updateReviewCount(roomId);
        updateFeedbackPoint(roomId);

        AutomaticUpdate automaticUpdate = getAutomaticUpdateByRoomId(roomId);
        automaticUpdate.updateStatusToDone();
    }

    private void updateReviewCount(long roomId) {
        matchResultRepository.findAllByRoomIdAndReviewStatus(roomId, ReviewStatus.COMPLETE)
                .forEach(this::increaseMembersReviewCountIn);
    }

    private void increaseMembersReviewCountIn(MatchResult matchResult) {
        Member reviewer = matchResult.getReviewer();
        reviewer.increaseReviewCount(MemberRole.REVIEWER);

        Member reviewee = matchResult.getReviewee();
        reviewee.increaseReviewCount(MemberRole.REVIEWEE);
    }

    private void updateFeedbackPoint(long roomId) {
        socialFeedbackRepository.findAllByRoomId(roomId)
                .forEach(this::updateSocialFeedbackPoint);

        developFeedbackRepository.findAllByRoomId(roomId)
                .forEach(this::updateDevelopFeedbackPoint);
    }

    private void updateSocialFeedbackPoint(SocialFeedback socialFeedback) {
        Member receiver = socialFeedback.getReceiver();
        receiver.updateAverageRating(socialFeedback.getEvaluatePoint());
    }

    private void updateDevelopFeedbackPoint(DevelopFeedback developFeedback) {
        Member receiver = developFeedback.getReceiver();
        receiver.updateAverageRating(developFeedback.getEvaluatePoint());
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }

    private AutomaticUpdate getAutomaticUpdateByRoomId(long roomId) {
        return automaticUpdateRepository.findByRoomId(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.AUTOMATIC_UPDATE_NOT_FOUND));
    }
}
