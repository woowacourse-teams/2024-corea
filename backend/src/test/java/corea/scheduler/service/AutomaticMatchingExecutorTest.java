package corea.scheduler.service;

import config.ServiceTest;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.MatchResult;
import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestResponse;
import corea.matching.repository.MatchResultRepository;
import corea.matching.service.PullRequestProvider;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ServiceTest
class AutomaticMatchingExecutorTest {

    @Autowired
    private AutomaticMatchingExecutor automaticMatchingExecutor;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @MockBean
    private PullRequestProvider pullRequestProvider;

    private Room room;

    @BeforeEach
    void setUp() {
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        Member joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        Member movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        Member ten = memberRepository.save(MemberFixture.MEMBER_TENTEN());
        Member cho = memberRepository.save(MemberFixture.MEMBER_CHOCO());

        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusSeconds(3)));

        participationRepository.save(new Participation(room, pororo.getId(), pororo.getGithubUserId()));
        participationRepository.save(new Participation(room, ash.getId(), ash.getGithubUserId()));
        participationRepository.save(new Participation(room, joysun.getId(), joysun.getGithubUserId()));
        participationRepository.save(new Participation(room, movin.getId(), movin.getGithubUserId()));
        participationRepository.save(new Participation(room, ten.getId(), ten.getGithubUserId()));
        participationRepository.save(new Participation(room, cho.getId(), cho.getGithubUserId()));

        Mockito.when(pullRequestProvider.getUntilDeadline(any(), any()))
                .thenReturn(new PullRequestInfo(Map.of(
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
                                LocalDateTime.of(2024, 10, 12, 18, 01)),
                        cho.getGithubUserId(),
                        new PullRequestResponse("link", new GithubUserResponse(cho.getGithubUserId()),
                                LocalDateTime.of(2024, 10, 12, 18, 01)
                        )
                )));
    }

    @Test
    @DisplayName("매칭을 진행한다.")
    void execute() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), room.getRecruitmentDeadline()));

        automaticMatchingExecutor.execute(automaticMatching.getRoomId());

        List<MatchResult> matchResults = matchResultRepository.findAll();
        assertThat(matchResults).isNotEmpty();
    }
}
