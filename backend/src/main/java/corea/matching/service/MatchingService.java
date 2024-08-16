package corea.matching.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.domain.MatchingStrategy;
import corea.matching.domain.PullRequestInfo;
import corea.matching.repository.MatchResultRepository;
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
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CoreaException(ExceptionType.ROOM_NOT_FOUND));

        List<Participation> participations = getParticipationsWithPullrequestSubmitted(pullRequestInfo, roomId);

        log.info("매칭 시작 [방 번호 ({}), 매칭하는 인원 ({}), 총 인원({})]", roomId, room.getMatchingSize(), participations.size());

        return matchResultRepository.saveAll(matchingStrategy.matchPairs(participations, room.getMatchingSize())
                .stream()
                .map(pair -> MatchResult.of(roomId, pair, pullRequestInfo.getPullrequestLinkWithGithubMemberId(pair.getToMemberGithubId())))
                .toList());
    }

    private List<Participation> getParticipationsWithPullrequestSubmitted(PullRequestInfo pullRequestInfo, long roomId) {
        return participationRepository.findAllByRoomId(roomId)
                .stream()
                .filter(participation -> pullRequestInfo.containsGithubMemberId(participation.getMemberGithubId()))
                .toList();
    }
}
