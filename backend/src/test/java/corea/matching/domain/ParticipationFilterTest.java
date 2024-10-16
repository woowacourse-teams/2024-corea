package corea.matching.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.infrastructure.dto.GithubUserResponse;
import corea.matching.infrastructure.dto.PullRequestResponse;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.room.domain.Room;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipationFilterTest {

    private Member pororo = MemberFixture.MEMBER_PORORO_WITH_ID(1L);
    private Member movin = MemberFixture.MEMBER_MOVIN_WITH_ID(2L);
    private Member joyson = MemberFixture.MEMBER_YOUNGSU_WITH_ID(3L);
    private Member choco = MemberFixture.MEMBER_CHOCO_WITH_ID(4L);

    private PullRequestInfo pullRequestInfo = pullRequestInfo();

    @Test
    @DisplayName("방 참여자의 수가 매칭 사이즈보다 작거나 같다면 예외가 발생한다.")
    void invalid() {

        Room movinRoom = RoomFixture.ROOM_DOMAIN(1L, movin);
        List<Participation> participations = List.of(
                new Participation(movinRoom, pororo,MemberRole.REVIEWER, ParticipationStatus.MANAGER, movinRoom.getMatchingSize()),
                new Participation(movinRoom, movin,MemberRole.REVIEWER, ParticipationStatus.MANAGER, movinRoom.getMatchingSize()));

        assertThatThrownBy(() -> new ParticipationFilter(participations, 2))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.PARTICIPANT_SIZE_LACK);
    }

    @Test
    @DisplayName("pr을 제출한 참가자들만 찾을 수 있다.")
    void filterPRSubmittedParticipation() {
        Room room = RoomFixture.ROOM_DOMAIN(1L, joyson);
        List<Participation> participations = List.of(
                new Participation(room, pororo, MemberRole.BOTH, 2),
                new Participation(room, movin, MemberRole.BOTH, 2),
                new Participation(room, joyson,MemberRole.REVIEWER, ParticipationStatus.MANAGER, 2),
                new Participation(room, choco, MemberRole.BOTH, 2)
        );

        ParticipationFilter participationFilter = new ParticipationFilter(participations, 2);
        List<Participation> participation = participationFilter.filterPRSubmittedParticipation(pullRequestInfo);

        List<Member> members = participation.stream()
                .map(Participation::getMember)
                .toList();

        assertThat(members).containsExactly(pororo, movin, joyson);
    }

    @Test
    @DisplayName("pr을 제출하지 않아 매칭 인원이 부족하면 예외가 발생한다.")
    void validatePRSubmittedParticipationSize() {
        Room room = RoomFixture.ROOM_DOMAIN(1L, pororo);
        List<Participation> participations = List.of(
                new Participation(room, pororo,MemberRole.REVIEWER, ParticipationStatus.MANAGER, room.getMatchingSize()),
                new Participation(room, movin, MemberRole.BOTH, 2),
                new Participation(room, choco, MemberRole.BOTH, 2)
        );

        ParticipationFilter participationFilter = new ParticipationFilter(participations, 2);

        assertThatThrownBy(() -> participationFilter.filterPRSubmittedParticipation(pullRequestInfo))
                .asInstanceOf(InstanceOfAssertFactories.type(CoreaException.class))
                .extracting(CoreaException::getExceptionType)
                .isEqualTo(ExceptionType.PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST);
    }

    private PullRequestInfo pullRequestInfo() {
        Map<String, PullRequestResponse> data = Map.of(
                pororo.getGithubUserId(),
                new PullRequestResponse("link", new GithubUserResponse(pororo.getGithubUserId()),
                        LocalDateTime.now()),

                joyson.getGithubUserId(),
                new PullRequestResponse("link", new GithubUserResponse(joyson.getGithubUserId()),
                        LocalDateTime.of(2024, 10, 12, 18, 30)),

                movin.getGithubUserId(),
                new PullRequestResponse("link", new GithubUserResponse(movin.getGithubUserId()),
                        LocalDateTime.of(2024, 10, 12, 18, 10))
        );

        return new PullRequestInfo(data);
    }
}
