package corea.scheduler.service;

import config.ServiceTest;
import config.TestAsyncConfig;
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
import corea.room.domain.RoomStatus;
import corea.room.repository.RoomRepository;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ServiceTest
@Import(TestAsyncConfig.class)
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
    private Room emptyParticipantRoom;
    private Member pororo;
    private Member ash;
    private Member joysun;
    private Member movin;
    private Member ten;
    private Member cho;

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

    @Test
    @DisplayName("매칭을 진행한다.")
    void execute() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), room.getRecruitmentDeadline()));

        automaticMatchingExecutor.execute(automaticMatching.getRoomId());

        List<MatchResult> matchResults = matchResultRepository.findAll();
        assertThat(matchResults).isNotEmpty();
    }

    @Test
    @DisplayName("동시에 10개의 자동 매칭을 실행해도 PESSIMISTIC_WRITE 락을 통해 동시성을 제어할 수 있다.")
    void startMatchingWithLock() throws InterruptedException {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), LocalDateTime.now().plusDays(1)));

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        when(pullRequestProvider.getUntilDeadline(any(), any())).thenAnswer(ignore -> {
            successCount.incrementAndGet();
            return getPullRequestInfo(pororo, ash, joysun, movin, ten, cho);
        });

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    automaticMatchingExecutor.execute(automaticMatching.getRoomId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        assertThat(successCount.get()).isEqualTo(1);
    }

    @Transactional
    @Test
    @DisplayName("매칭 시도 중 예외가 발생했다면 방 상태를 FAIL로 변경한다.")
    void matchFail() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(emptyParticipantRoom.getId(), emptyParticipantRoom.getRecruitmentDeadline()));

        automaticMatchingExecutor.execute(automaticMatching.getRoomId());

        assertThat(emptyParticipantRoom.getStatus()).isEqualTo(RoomStatus.FAIL);
    }
}
