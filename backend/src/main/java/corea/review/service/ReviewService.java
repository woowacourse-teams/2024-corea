package corea.review.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.domain.MatchResultReader;
import corea.matchresult.domain.MatchResultWriter;
import corea.review.dto.GithubPullRequestReview;
import corea.review.dto.GithubPullRequestReviewInfo;
import corea.review.infrastructure.GithubReviewProvider;
import corea.room.domain.RoomReader;
import corea.room.domain.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private static final Logger log = LogManager.getLogger(ReviewService.class);

    private final GithubReviewProvider githubReviewProvider;
    private final RoomReader roomReader;
    private final MatchResultReader matchResultReader;
    private final MatchResultWriter matchResultWriter;

    @Transactional
    public void completeReview(long roomId, long reviewerId, long revieweeId) {
        boolean isNotProgress = roomReader.isNotStatus(roomId, RoomStatus.PROGRESS);
        if (isNotProgress) {
            throw new CoreaException(ExceptionType.ROOM_STATUS_INVALID);
        }
        MatchResult matchResult = matchResultReader.findOne(roomId, reviewerId, revieweeId);
        String reviewLink = getPrReviewLink(matchResult.getPrLink(), matchResult.getReviewerGithubId());
        matchResultWriter.reviewComplete(matchResult, reviewLink);

        log.info("리뷰 완료[{매칭 ID({}), 리뷰어 ID({}, 리뷰이 ID({})", matchResult.getId(), reviewerId, revieweeId);
    }

    private String getPrReviewLink(String prLink, String reviewerGithubId) {
        GithubPullRequestReviewInfo reviewInfo = githubReviewProvider.provideReviewInfo(prLink);

        return reviewInfo.findWithGithubUserId(reviewerGithubId)
                .map(GithubPullRequestReview::html_url)
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_COMPLETE_GITHUB_REVIEW));
    }
}
