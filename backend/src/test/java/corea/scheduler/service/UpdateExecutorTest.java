package corea.scheduler.service;

import corea.room.domain.RoomStatus;
import corea.scheduler.domain.AutomaticUpdate;
import corea.scheduler.repository.AutomaticUpdateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateExecutorTest extends UpdateTest {

    @Autowired
    private UpdateExecutor updateExecutor;

    @Autowired
    private AutomaticUpdateRepository automaticUpdateRepository;

    @Transactional
    @Test
    @DisplayName("방 상태를 변경한다.")
    void execute() {
        AutomaticUpdate automaticUpdate = automaticUpdateRepository.save(new AutomaticUpdate(room.getId(), room.getReviewDeadline()));

        updateExecutor.update(automaticUpdate.getRoomId());

        assertThat(room.getStatus()).isEqualTo(RoomStatus.CLOSE);
    }
}
