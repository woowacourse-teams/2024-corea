package corea.matching.service;

import config.ServiceTest;
import corea.exception.CoreaException;
import corea.matching.dto.MatchResultResponses;
import corea.participation.domain.Participation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static corea.exception.ExceptionType.MEMBER_NOT_FOUND;
import static corea.exception.ExceptionType.ROOM_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
@ActiveProfiles("test")
@Transactional
class MatchResultServiceTest {

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchResultService matchResultService;

    @Test
    @DisplayName("사용자가 특정 방에서 매칭된 리뷰어 결과를 가져온다.")
    void findReviewers() {
        long memberId = 1L;
        long roomId = 1L;
        int matchingSize = 3;
        List<Participation> participations = new ArrayList<>();

        participations.add(new Participation(roomId, 1L));
        participations.add(new Participation(roomId, 2L));
        participations.add(new Participation(roomId, 3L));
        participations.add(new Participation(roomId, 4L));

        matchingService.matchMaking(participations, matchingSize);

        MatchResultResponses reviewers = matchResultService.findReviewers(memberId, roomId);

        assertThat(reviewers.matchResultResponses()).hasSize(matchingSize);
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException() {
        long memberId = 1;
        long roomId = 0;

        int matchingSize = 3;
        List<Participation> participations = new ArrayList<>();

        participations.add(new Participation(1L, 1L));
        participations.add(new Participation(1L, 4L));
        participations.add(new Participation(1L, 5L));
        participations.add(new Participation(1L, 6L));
        participations.add(new Participation(1L, 7L));

        matchingService.matchMaking(participations, matchingSize);

        assertThatThrownBy(() -> matchResultService.findReviewers(memberId, roomId))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(ROOM_NOT_FOUND);
                });
    }

    @Test
    @DisplayName("리뷰어 결과를 가져올 때 존재하지 않는 방이나 사용자의 정보를 요청하는 경우 예외를 발생한다.")
    void findReviewersInvalidException2() {
        long memberId = 0;
        long roomId = 1;
        int matchingSize = 3;
        List<Participation> participations = new ArrayList<>();

        participations.add(new Participation(1L, 1L));
        participations.add(new Participation(1L, 4L));
        participations.add(new Participation(1L, 5L));
        participations.add(new Participation(1L, 6L));
        participations.add(new Participation(1L, 7L));

        matchingService.matchMaking(participations, matchingSize);

        assertThatThrownBy(() -> matchResultService.findReviewers(memberId, roomId))
                .isInstanceOf(CoreaException.class)
                .satisfies(exception -> {
                    CoreaException coreaException = (CoreaException) exception;
                    assertThat(coreaException.getExceptionType()).isEqualTo(MEMBER_NOT_FOUND);
                });
    }
}
