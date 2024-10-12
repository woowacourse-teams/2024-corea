package corea.review.service;

import corea.auth.dto.GithubPullRequestReview;
import corea.auth.service.GithubOAuthProvider;
import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private static final Logger log = LogManager.getLogger(ReviewService.class);

    private final GithubOAuthProvider githubOAuthProvider;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public void completeReview(long roomId, long reviewerId, long revieweeId) {
        Room room = getRoom(roomId);
        validateRoomStatus(room);

        MatchResult matchResult = getMatchResult(roomId, reviewerId, revieweeId);
        matchResult.reviewComplete();
        updateReviewLink(matchResult, reviewerId);

        log.info("리뷰 완료[{매칭 ID({}), 리뷰어 ID({}, 리뷰이 ID({})", matchResult.getId(), reviewerId, revieweeId);
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("해당 Id의 방이 없습니다. 입력된 Id=%d", roomId)));
    }

    private void validateRoomStatus(Room room) {
        if (room.isNotProgress()) {
            throw new CoreaException(ExceptionType.ROOM_STATUS_INVALID);
        }
    }

    private void updateReviewLink(MatchResult matchResult, long reviewerId) {
        Member reviewer = memberRepository.findById(reviewerId)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
        String userName = reviewer.getUsername();
        String newReviewLink = findReviewLink(userName, matchResult.getPrLink());
        matchResult.updateReviewLink(newReviewLink);
    }

    private String findReviewLink(String userName, String prLink) {
        GithubPullRequestReview[] githubPullRequestReviews = githubOAuthProvider.getPullRequestReview(prLink);
        return Stream.of(githubPullRequestReviews)
                .filter(review -> review.user().login().equals(userName))
                .findFirst()
                .map(GithubPullRequestReview::html_url)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_COMPLETE_GITHUB_REVIEW));
    }

    private MatchResult getMatchResult(long roomId, long reviewerId, long revieweeId) {
        return matchResultRepository.findByRoomIdAndReviewerIdAndRevieweeId(roomId, reviewerId, revieweeId)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_MATCHED_MEMBER));
    }
}
