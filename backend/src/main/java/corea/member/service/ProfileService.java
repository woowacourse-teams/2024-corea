package corea.member.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.feedback.domain.FeedbackKeyword;
import corea.feedback.domain.ReceivedFeedbacks;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.member.domain.Member;
import corea.member.dto.ProfileResponse;
import corea.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private static final int NUMBER_OF_FEEDBACKS_TO_BE_SHOWN_TO_MEMBER = 3;

    private final MemberRepository memberRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;

    public ProfileResponse findProfileInfoByUsername(String username) {
        Member member = getByUserName(username);
        List<String> topFeedbackKeywords = findTopFeedbackKeywords(member.getId());

        return ProfileResponse.of(member, topFeedbackKeywords);
    }

    public ProfileResponse findProfileInfoById(long id) {
        Member member = getById(id);
        List<String> topFeedbackKeywords = findTopFeedbackKeywords(id);

        return ProfileResponse.of(member, topFeedbackKeywords);
    }

    private List<String> findTopFeedbackKeywords(long memberId) {
        List<List<FeedbackKeyword>> socialFeedbackKeywords = socialFeedbackRepository.findAllKeywordsByReceiverId(memberId);
        List<List<FeedbackKeyword>> developFeedbackKeywords = developFeedbackRepository.findAllKeywordsByReceiverId(memberId);
        ReceivedFeedbacks receivedFeedbacks = new ReceivedFeedbacks(socialFeedbackKeywords, developFeedbackKeywords);

        return receivedFeedbacks.findTopFeedbackKeywords(NUMBER_OF_FEEDBACKS_TO_BE_SHOWN_TO_MEMBER);
    }

    private Member getById(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
    }

    private Member getByUserName(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new CoreaException(ExceptionType.MEMBER_NOT_FOUND));
    }
}
