package corea.scheduler.service;

import config.ServiceTest;
import config.TestAsyncConfig;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestResponse;
import corea.matching.service.PullRequestProvider;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ServiceTest
@Import(TestAsyncConfig.class)
public abstract class MatchingTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @MockBean
    protected PullRequestProvider pullRequestProvider;

    protected Room room;
    protected Room emptyParticipantRoom;
    protected Member pororo;
    protected Member ash;
    protected Member joysun;
    protected Member movin;
    protected Member ten;
    protected Member cho;

    @BeforeEach
    void setUp() {
        pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        ten = memberRepository.save(MemberFixture.MEMBER_TENTEN());
        cho = memberRepository.save(MemberFixture.MEMBER_CHOCO());

        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusSeconds(3)));
        emptyParticipantRoom = roomRepository.save(RoomFixture.ROOM_DOMAIN(ash, LocalDateTime.now().plusSeconds(3)));

        participationRepository.save(new Participation(room, pororo, MemberRole.BOTH, room.getMatchingSize()));
        participationRepository.save(new Participation(room, ash, MemberRole.BOTH, room.getMatchingSize()));
        participationRepository.save(new Participation(room, joysun, MemberRole.BOTH, room.getMatchingSize()));
        participationRepository.save(new Participation(room, movin, MemberRole.BOTH, room.getMatchingSize()));
        participationRepository.save(new Participation(room, ten, MemberRole.BOTH, room.getMatchingSize()));
        participationRepository.save(new Participation(room, cho, MemberRole.BOTH, room.getMatchingSize()));

        when(pullRequestProvider.getUntilDeadline(any(), any()))
                .thenReturn(getPullRequestInfo(pororo, ash, joysun, movin, ten, cho));
    }

    protected PullRequestInfo getPullRequestInfo(Member pororo, Member ash, Member joysun, Member movin, Member ten, Member cho) {
        return new PullRequestInfo(Map.of(
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
        ));
    }
}
