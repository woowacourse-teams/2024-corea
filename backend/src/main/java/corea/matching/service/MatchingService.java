package corea.matching.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.ParticipationFilter;
import corea.matching.domain.PullRequestInfo;
import corea.matching.strategy.MatchingStrategy;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    private static final Logger log = LoggerFactory.getLogger(MatchingService.class);

    private final MatchingStrategy matchingStrategy;
    private final MatchResultRepository matchResultRepository;

    private final ParticipationRepository participationRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public List<MatchResult> match(long roomId, PullRequestInfo pullRequestInfo) {
        Room room = getRoom(roomId);
        room.updateStatusToProgress();

        List<MatchResult> matchResults = matchPairs(room, pullRequestInfo);
        logMatchResults(roomId,matchResults);
        return matchResultRepository.saveAll(matchResults);
    }

    private List<MatchResult> matchPairs(Room room, PullRequestInfo pullRequestInfo) {
        long roomId = room.getId();
        int matchingSize = room.getMatchingSize();
        List<Participation> prSubmittedParticipation = findPRSubmittedParticipation(room, pullRequestInfo);

        log.info("매칭 시작 [방 번호 ({}), 매칭하는 인원 ({}), 총 인원({})]", roomId, matchingSize, prSubmittedParticipation.size());

        return matchingStrategy.matchPairs(prSubmittedParticipation, matchingSize)
                .stream()
                .map(pair -> MatchResult.of(roomId, pair, pullRequestInfo.getPullrequestLinkWithGithubMemberId(pair.getReceiverGithubId())))
                .toList();
    }

    private void logMatchResults(long roomId, List<MatchResult> matchResults) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("매칭 결과 [방 번호: ").append(roomId).append("]\n");

        matchResults.forEach(result ->
                logMessage.append("리뷰어: ").append(result.getReviewer().getUsername())
                        .append(", 리뷰이: ").append(result.getReviewee().getUsername())
                        .append(", PR 링크: ").append(result.getPrLink()).append("\n")
        );

        log.info(logMessage.toString());
    }

    private List<Participation> findPRSubmittedParticipation(Room room, PullRequestInfo pullRequestInfo) {
        List<Participation> participations = participationRepository.findAllByRoom(room)
                .stream()
                .toList();

        ParticipationFilter participationFilter = new ParticipationFilter(participations, room.getMatchingSize());
        return participationFilter.filterPRSubmittedParticipation(pullRequestInfo);
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }
}
