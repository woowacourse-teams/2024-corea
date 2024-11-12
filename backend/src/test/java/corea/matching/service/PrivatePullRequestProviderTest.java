package corea.matching.service;

import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.matching.domain.PullRequestInfo;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationStatus;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PrivatePullRequestProviderTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PrivatePullRequestProvider privatePullRequestProvider;

    @Test
    @DisplayName("각 레포지토리에서 데이터를 가져온다.")
    @Disabled
    void find_each_repository() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        String repositoryLink = "https://github.com/woowacourse-precourse/java-christmas-6";
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(repositoryLink, manager));
        Member choco = memberRepository.save(MemberFixture.MEMBER_CHOCO());
        Member pororo = memberRepository.save(MemberFixture.MEMBER_PORORO_GITHUB());

        participationRepository.save(new Participation(room, pororo, MemberRole.REVIEWEE, ParticipationStatus.PARTICIPATED, 2));
        participationRepository.save(new Participation(room, choco, MemberRole.REVIEWEE, ParticipationStatus.PARTICIPATED, 3));
        PullRequestInfo info = privatePullRequestProvider.getEachRepository(room.getId());
        assertThat(info.containsGithubMemberId(pororo.getGithubUserId())).isTrue();
        assertThat(info.containsGithubMemberId(choco.getGithubUserId())).isFalse();
    }
}
