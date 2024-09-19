package corea.scheduler.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.domain.MatchingStrategy;
import corea.matching.domain.PullRequestInfo;
import corea.matching.repository.MatchResultRepository;
import corea.matching.service.PullRequestProvider;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutomaticMatchingExecutor {

    private final MatchingStrategy matchingStrategy;
    private final PullRequestProvider pullRequestProvider;
    private final RoomRepository roomRepository;
    private final MatchResultRepository matchResultRepository;
    private final ParticipationRepository participationRepository;

    @Async
    @Transactional
    public void execute(AutomaticMatching automaticMatching) {
        List<MatchResult> matchResults = startMatching(automaticMatching.getRoomId());
        matchResultRepository.saveAll(matchResults);

        automaticMatching.updateStatusToDone();
    }

    private List<MatchResult> startMatching(long roomId) {
        Room room = getRoom(roomId);
        PullRequestInfo pullRequestInfo = pullRequestProvider.getUntilDeadline(room.getRepositoryLink(), room.getRecruitmentDeadline());
        List<Participation> participations = getParticipationsWithPullrequestSubmitted(pullRequestInfo, roomId);

        log.info("매칭 시작 [방 번호 ({}), 매칭하는 인원 ({}), 총 인원({})]", roomId, room.getMatchingSize(), participations.size());

        return matchPairs(roomId, participations, room, pullRequestInfo);
    }

    private Room getRoom(long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));
    }

    private List<Participation> getParticipationsWithPullrequestSubmitted(PullRequestInfo pullRequestInfo, long roomId) {
        return participationRepository.findAllByRoomId(roomId)
                .stream()
                .filter(participation -> pullRequestInfo.containsGithubMemberId(participation.getMemberGithubId()))
                .toList();
    }

    private List<MatchResult> matchPairs(long roomId, List<Participation> participations, Room room, PullRequestInfo pullRequestInfo) {
        return matchingStrategy.matchPairs(participations, room.getMatchingSize())
                .stream()
                .map(pair -> MatchResult.of(roomId, pair, pullRequestInfo.getPullrequestLinkWithGithubMemberId(pair.getReceiverGithubId())))
                .toList();
    }
}
