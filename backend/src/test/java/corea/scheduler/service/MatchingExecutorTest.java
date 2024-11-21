package corea.scheduler.service;

import config.ServiceTest;
import config.TestAsyncConfig;
import corea.alarm.domain.AlarmActionType;
import corea.alarm.domain.ServerToUserAlarm;
import corea.alarm.repository.ServerToUserAlarmRepository;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.PullRequestInfo;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestResponse;
import corea.matching.service.PullRequestProvider;
import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomMatchInfo;
import corea.room.domain.RoomStatus;
import corea.room.repository.RoomMatchInfoRepository;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ServiceTest
@Import(TestAsyncConfig.class)
class MatchingExecutorTest {

    @Autowired
    private MatchingExecutor matchingExecutor;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ServerToUserAlarmRepository serverToUserAlarmRepository;

    @MockBean
    private PullRequestProvider pullRequestProvider;

    private Room room;
    private Room emptyParticipantRoom;
    private Member pororo;
    private Member ash;
    private Member joysun;
    private Member movin;
    private Member ten;
    private Member cho;

    @Autowired
    private RoomMatchInfoRepository roomMatchInfoRepository;

    @BeforeEach
    void setUp() {
        pororo = memberRepository.save(MemberFixture.MEMBER_PORORO());
        ash = memberRepository.save(MemberFixture.MEMBER_ASH());
        joysun = memberRepository.save(MemberFixture.MEMBER_YOUNGSU());
        movin = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        ten = memberRepository.save(MemberFixture.MEMBER_TENTEN());
        cho = memberRepository.save(MemberFixture.MEMBER_CHOCO());

        room = roomRepository.save(RoomFixture.ROOM_DOMAIN(pororo, LocalDateTime.now().plusSeconds(3)));
        roomMatchInfoRepository.save(new RoomMatchInfo(room.getId(), true));
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

    private PullRequestInfo getPullRequestInfo(Member pororo, Member ash, Member joysun, Member movin, Member ten, Member cho) {
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

    @AfterEach
    void tearDown() {
        serverToUserAlarmRepository.deleteAll();
    }

    @Test
    @DisplayName("매칭을 진행한다.")
    void match() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), room.getRecruitmentDeadline()));

        matchingExecutor.match(automaticMatching.getRoomId());

        List<MatchResult> matchResults = matchResultRepository.findAll();
        assertThat(matchResults).isNotEmpty();
    }

    @Test
    @DisplayName("매칭이 완료되면 매칭 완료 알람이 생성된다.")
    void matchCompleteAlarm() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), room.getRecruitmentDeadline()));

        matchingExecutor.match(automaticMatching.getRoomId());

        List<ServerToUserAlarm> serverToUserAlarms = serverToUserAlarmRepository.findAll();

        assertAll(
                () -> assertThat(serverToUserAlarms).isNotEmpty(),
                () -> assertEquals(serverToUserAlarms.get(0).getAlarmActionType(), AlarmActionType.MATCH_COMPLETE)
        );
    }

    @Transactional
    @Test
    @DisplayName("매칭 시도 중 예외가 발생했다면 방 상태를 FAIL로 변경한다.")
    void matchFail() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(emptyParticipantRoom.getId(), emptyParticipantRoom.getRecruitmentDeadline()));

        matchingExecutor.match(automaticMatching.getRoomId());

        assertThat(emptyParticipantRoom.getStatus()).isEqualTo(RoomStatus.FAIL);
    }

    // 이 부분 트랜잭션 문제 같은데 정확히 모르겠어서 같이 봐주셨으면 합니당...
    @Disabled
    @Transactional
    @Test
    @DisplayName("매칭이 실패하면 매칭 실패 알람이 생성된다.")
    void matchFailAlarm() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(emptyParticipantRoom.getId(), emptyParticipantRoom.getRecruitmentDeadline()));

        matchingExecutor.match(automaticMatching.getRoomId());
        List<ServerToUserAlarm> serverToUserAlarms = serverToUserAlarmRepository.findAll();

        assertAll(
                () -> assertThat(emptyParticipantRoom.getStatus()).isEqualTo(RoomStatus.FAIL),
                () -> assertThat(serverToUserAlarms).isNotEmpty(),
                () -> assertEquals(serverToUserAlarms.get(0).getAlarmActionType(), AlarmActionType.MATCH_FAIL)
        );
    }
}
