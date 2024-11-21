package corea.feedback.repository;

import config.RepositoryTest;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.FeedbackKeyword;
import corea.fixture.MemberFixture;
import corea.fixture.RoomFixture;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Room;
import corea.room.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class DevelopFeedbackRepositoryTest {

    @Autowired
    private DevelopFeedbackRepository developFeedbackRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("받은 전체 개발 피드백을 조회할 수 있다.")
    void findAllKeywordsByReceiverId() {
        Member manager = memberRepository.save(MemberFixture.MEMBER_ROOM_MANAGER_JOYSON());
        Room room = roomRepository.save(RoomFixture.ROOM_DOMAIN(manager));

        Member deliver = memberRepository.save(MemberFixture.MEMBER_PORORO());
        Member receiver = memberRepository.save(MemberFixture.MEMBER_MOVIN());
        List<FeedbackKeyword> feedbackKeywords = List.of(FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_1, FeedbackKeyword.POSITIVE_DEVELOP_FEEDBACK_2);
        DevelopFeedback feedback = new DevelopFeedback(room.getId(), deliver, receiver, 3, feedbackKeywords, "", 3);
        developFeedbackRepository.save(feedback);

        List<List<FeedbackKeyword>> keywords = developFeedbackRepository.findAllKeywordsByReceiverId(receiver.getId());

        assertThat(keywords.get(0)).isEqualTo(feedbackKeywords);
    }
}
