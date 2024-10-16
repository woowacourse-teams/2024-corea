package corea.scheduler.service;

import config.ServiceTest;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.ParticipationStatus;
import corea.room.domain.Room;
import corea.room.dto.RoomResponse;
import corea.room.repository.RoomRepository;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class AutomaticMatchingSchedulerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @Autowired
    private AutomaticMatchingExecutor automaticMatchingExecutor;

    private Map<Long, ScheduledFuture<?>> scheduledTasks;
    private AutomaticMatchingScheduler automaticMatchingScheduler;

    @BeforeEach
    void setup() {
        this.scheduledTasks = new HashMap<>();
        this.automaticMatchingScheduler = new AutomaticMatchingScheduler(taskScheduler, automaticMatchingExecutor, scheduledTasks);
    }

    @Test
    @DisplayName("마감 기한에 맞게 자동 업데이트를 등록한다.")
    void updateAtReviewDeadline() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime reviewDeadline = LocalDateTime.now()
                .plusDays(2);
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_REVIEW_DEADLINE(manager, reviewDeadline));

        automaticMatchingScheduler.matchOnRecruitmentDeadline(room);

        assertThat(scheduledTasks.containsKey(room.getId())).isTrue();
    }

    @Test
    @DisplayName("예약된 자동 업데이트를 삭제한다.")
    void cancel() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime reviewDeadline = LocalDateTime.now()
                .plusDays(2);
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_REVIEW_DEADLINE(manager, reviewDeadline));
        automaticMatchingScheduler.matchOnRecruitmentDeadline(room);
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(room.getId());

        automaticMatchingScheduler.cancel(room.getId());

        assertThat(scheduledFuture.isCancelled()).isTrue();
        assertThat(scheduledTasks.containsKey(room.getId())).isFalse();
    }

    @Test
    @DisplayName("예약된 자동 업데이트를 수정한다.")
    void update() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        LocalDateTime reviewDeadline = LocalDateTime.now()
                .plusDays(2);
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN_REVIEW_DEADLINE(manager, reviewDeadline));
        automaticMatchingScheduler.matchOnRecruitmentDeadline(room);
        ScheduledFuture<?> task = scheduledTasks.get(room.getId());

        automaticMatchingScheduler.modifyTask(room);
        ScheduledFuture<?> updateTask = scheduledTasks.get(room.getId());

        assertThat(task.isCancelled()).isTrue();
        assertThat(updateTask.isCancelled()).isFalse();
    }


//    @Test
//    @DisplayName("모집 마감 기한이 되면 매칭을 자동으로 진행한다.")
//    void matchOnRecruitmentDeadline() {
//        // 현재 시간으로부터 10시간 후로 모집 마감 시간 설정
//        LocalDateTime recruitmentDeadline = LocalDateTime.now()
//                .plusHours(10);
//        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse(recruitmentDeadline));
//
//        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(mock(ScheduledFuture.class));
//
//        RoomResponse response = roomService.create(anyLong(), any());
//        automaticMatchingRepository.save(new AutomaticMatching(response.id(), response.recruitmentDeadline()));
//
//        // taskScheduler를 사용하는 메소드 호출
//
//        // taskScheduler.schedule 메소드에 전달된 인자를 캡처하기 위한 ArgumentCaptor 설정
//        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
//        ArgumentCaptor<Instant> timeCaptor = ArgumentCaptor.forClass(Instant.class);
//        // taskScheduler.schedule 메소드가 호출되었는지 확인하고 전달된 인자 캡처
//        verify(taskScheduler).schedule(runnableCaptor.capture(), timeCaptor.capture());
//        Instant scheduledTime = timeCaptor.getValue();
//        runnableCaptor.getValue()
//                .run();
//
//        // 예약된 시간이 설정한 모집 마감 시간과 일치하는지 확인
//        assertThat(recruitmentDeadline.atZone(ZoneId.of("Asia/Seoul"))
//                .toInstant()).isEqualTo(scheduledTime);
//        // automaticMatchingExecutor.execute 메소드가 호출되었는지 확인
//        verify(automaticMatchingExecutor).execute(response.id());
//    }
//
//    @Test
//    @DisplayName("예약된 자동 매칭을 삭제한다.")
//    void cancel() {
//        LocalDateTime recruitmentDeadline = LocalDateTime.now()
//                .plusHours(10);
//        when(roomService.create(anyLong(), any())).thenReturn(getRoomResponse(recruitmentDeadline));
//        ScheduledFuture scheduledFuture = mock(ScheduledFuture.class);
//        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class))).thenReturn(scheduledFuture);
//
//        RoomResponse response = roomService.create(anyLong(), any());
//        roomService.delete(anyLong(), anyLong());
//        automaticMatchingRepository.save(new AutomaticMatching(response.id(), response.recruitmentDeadline()));
//
//        verify(scheduledFuture).cancel(true);
//    }

    private RoomResponse getRoomResponse(LocalDateTime recruitmentDeadline) {
        return new RoomResponse(10,
                "title",
                "content",
                "managerName",
                "repolink",
                "link",
                2,
                List.of(),
                1,
                10,
                recruitmentDeadline,
                LocalDateTime.now()
                        .plusDays(3),
                ParticipationStatus.PARTICIPATED,
                MemberRole.NONE,
                "OPEN",
                "");
    }
}
