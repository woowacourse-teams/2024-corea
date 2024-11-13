package corea.matching.service;

import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.PrivateGithubPullRequestClient;
import corea.matching.infrastructure.dto.PullRequestResponse;
import corea.member.domain.MemberReader;
import corea.participation.domain.ParticipationReader;
import corea.room.domain.Room;
import corea.room.domain.RoomReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PrivatePullRequestProvider {

    private final PrivateGithubPullRequestClient privateGithubPullRequestClient;
    private final RoomReader roomReader;
    private final MemberReader memberReader;
    private final ParticipationReader participationReader;


    public PullRequestInfo getEachRepository(long roomId) {
        Room room = roomReader.find(roomId);
        List<Long> memberIds = participationReader.findRevieweeIdsByRoomId(roomId);
        List<String> members = memberReader.findUsernamesByIds(memberIds);

        return new PullRequestInfo(members.stream()
                .map(username -> privateGithubPullRequestClient.getPullRequest(room.getRepositoryLink(), username))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(PullRequestResponse::getUserId, Function.identity())));
    }
}
