package corea.scheduler.service;

import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.room.domain.RoomStatus;
import corea.scheduler.domain.AutomaticMatching;
import corea.scheduler.repository.AutomaticMatchingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MatchingExecutorTest extends MatchingTest {

    @Autowired
    private MatchingExecutor matchingExecutor;

    @Autowired
    private AutomaticMatchingRepository automaticMatchingRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Test
    @DisplayName("매칭을 진행한다.")
    void match() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(room.getId(), room.getRecruitmentDeadline()));

        matchingExecutor.match(automaticMatching.getRoomId());

        List<MatchResult> matchResults = matchResultRepository.findAll();
        assertThat(matchResults).isNotEmpty();
    }

    @Transactional
    @Test
    @DisplayName("매칭 시도 중 예외가 발생했다면 방 상태를 FAIL로 변경한다.")
    void matchFail() {
        AutomaticMatching automaticMatching = automaticMatchingRepository.save(new AutomaticMatching(emptyParticipantRoom.getId(), emptyParticipantRoom.getRecruitmentDeadline()));

        matchingExecutor.match(automaticMatching.getRoomId());

        assertThat(emptyParticipantRoom.getStatus()).isEqualTo(RoomStatus.FAIL);
    }
}
