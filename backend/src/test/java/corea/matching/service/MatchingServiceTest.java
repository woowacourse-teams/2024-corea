package corea.matching.service;

import config.ServiceTest;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestResponse;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class MatchingServiceTest {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    private long roomId;
    private Member noPullRequestMember;

    @Test
    @DisplayName("PR 정보에 있는 멤버만 매칭이 된다.")
    void matched_with_pr_info_members() {
        PullRequestInfo pullRequestInfo = initialize();
        List<MatchResult> matchResults = matchingService.match(roomId, pullRequestInfo);
        boolean isExisted = matchResults.stream()
                .anyMatch(matchResult -> noPullRequestMember.equals(matchResult.getReviewee()) && noPullRequestMember.equals(matchResult.getReviewer()));

        assertThat(isExisted).isFalse();
    }

    private PullRequestInfo initialize() {
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_WITH_DEADLINE(memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON()),
                LocalDateTime.of(2024, 10, 12, 17, 00)));
        roomId = room.getId();
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MUBIN());
        Member ten = memberRepository.save(MemberFixture.MEMBER_TENTEN());
        Member cho = memberRepository.save(MemberFixture.MEMBER_CHOCO());
        noPullRequestMember = cho;

        participationRepository.save(new Participation(roomId, pororo.getId(), pororo.getGithubUserId()));
        participationRepository.save(new Participation(roomId, ash.getId(), ash.getGithubUserId()));
        participationRepository.save(new Participation(roomId, joysun.getId(), joysun.getGithubUserId()));
        participationRepository.save(new Participation(roomId, movin.getId(), movin.getGithubUserId()));
        participationRepository.save(new Participation(roomId, ten.getId(), ten.getGithubUserId()));
        participationRepository.save(new Participation(roomId, cho.getId(), cho.getGithubUserId()));


        return new PullRequestInfo(
                Map.of(
                        pororo.getGithubUserId(),
                        new PullRequestResponse("link", new GithubUserResponse(pororo.getGithubUserId()),
                                LocalDateTime.of(2024, 10, 12, 18, 00)),
                        ash.getGithubUserId(),
                        new PullRequestResponse("link", new GithubUserResponse(ash.getGithubUserId()),
                                LocalDateTime.of(2024, 10, 12, 18, 20)),
                        joysun.getGithubUserId(),
                        new PullRequestResponse("link", new GithubUserResponse(joysun.getGithubUserId()),
                                LocalDateTime.of(2024, 10, 12, 18, 30)),
                        movin.getGithubUserId(),
                        new PullRequestResponse("link", new GithubUserResponse(movin.getGithubUserId()),
                                LocalDateTime.of(2024, 10, 12, 18, 10)),
                        ten.getGithubUserId(),
                        new PullRequestResponse("link", new GithubUserResponse(ten.getGithubUserId()),
                                LocalDateTime.of(2024, 10, 12, 18, 01)
                        )
                ));
    }
}
