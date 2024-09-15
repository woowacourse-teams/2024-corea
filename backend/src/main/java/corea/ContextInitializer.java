package corea;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.domain.MatchingStrategy;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static corea.feedback.domain.FeedbackKeyword.*;

@Profile({"dev","local"})
@Component
@Transactional
@RequiredArgsConstructor
public class ContextInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final MatchResultRepository matchResultRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;
    private final MatchingStrategy matchingStrategy;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        initialize();
        Member pororo = memberRepository.save(
                new Member("jcoding-play", "https://avatars.githubusercontent.com/u/119468757?v=4", "조경찬",
                        "pororo@email.com", true, "119468757"));
        Member ash = memberRepository.save(
                new Member("ashsty", "https://avatars.githubusercontent.com/u/77227961?v=4", "박민아",
                        "ash@email.com", false, "77227961"));
        Member joysun = memberRepository.save(
                new Member("youngsu5582", "https://avatars.githubusercontent.com/u/98307410?v=4", "이영수",
                        "joysun@email.com", false, "98307410"));
        Member movin = memberRepository.save(
                new Member("hjk0761", "https://avatars.githubusercontent.com/u/80106238?s=96&v=4", "김현중",
                        "movin@email.com", true, "80106238"));
        Member ten = memberRepository.save(
                new Member("chlwlstlf", "https://avatars.githubusercontent.com/u/63334368?v=4", "최진실",
                        "tenten@email.com", true, "63334368"));
        Member cho = memberRepository.save(
                new Member("00kang", "https://avatars.githubusercontent.com/u/70834044?v=4", "강다빈",
                        "choco@email.com", true, "70834044"));
        Member dar = memberRepository.save(
                new Member("pp449", "https://avatars.githubusercontent.com/u/71641127?v=4", "이상엽",
                        "darr@email.com", true, "71641127"));

        // 이미 모집 완료되어 매칭까지 진행된 방
        Room room1 = roomRepository.save(
                new Room("자바 레이싱 카 - MVC", "MVC 패턴을 아시나요?", 2,
                        "https://github.com/woowacourse/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("MVC", "자바", "디자인패턴"), 6, 30, pororo,
                        LocalDateTime.now()
                                .minusDays(7),
                        LocalDateTime.now()
                                .minusDays(1),
                        RoomClassification.BACKEND, RoomStatus.CLOSE));

        // ash 기준 방장인 방
        Room room2 = roomRepository.save(
                new Room("자바 체스 - TDD", "애쉬와 함께하는 TDD", 3,
                        "https://github.com/woowacourse/java-chess",
                        "https://i.namu.wiki/i/ZuKHR3uGK77VBnvkBO9f1XrAdne3sKlaNR_a-no5alODVhSxhbt4_BnUFPJ1zQMMzkNrvdvpEYvXVt2BBAhIuMpotu6H6ua-Ou5ps01yKD66rukqtW2sCdAuyYUSg_bSngRl6b-tlt5umrKxwd5olQ.webp",
                        List.of("TDD", "클린코드", "자바"), 6, 30, ash,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.BACKEND, RoomStatus.CLOSE));

        // 애쉬가 참여했는데 끝난 방
        Room room3 = roomRepository.save(
                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 2,
                        "https://github.com/woowacourse-precourse/java-baseball-6",
                        "https://media.istockphoto.com/id/1471217278/ko/%EC%82%AC%EC%A7%84/%EC%95%BC%EA%B5%AC-%EA%B2%BD%EA%B8%B0%EC%9E%A5-%EA%B2%BD%EA%B8%B0%EC%9E%A5%EC%9D%98-%EC%9E%94%EB%94%94%EC%97%90-%EC%95%BC%EA%B5%AC-%EA%B3%B5.jpg?s=612x612&w=0&k=20&c=2comykS41HVEZgI5l9nPUVh4kmT-oxAOKmgHGAgK2aM=",
                        List.of("코틀린"), 6, 20, movin,
                        LocalDateTime.now()
                                .minusDays(14),
                        LocalDateTime.now()
                                .minusDays(2),
                        RoomClassification.ANDROID, RoomStatus.CLOSE));

        // 애쉬가 참여 가능한 방들
        Room room4 = roomRepository.save(
                new Room("자바 로또 - 객체지향", "객체지향 한 접시", 2,
                        "https://github.com/woowacourse-precourse/java-lotto-6",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYqN2oNe2BBVwm_lxJ6BbWS13Mkb9lmohGUw&s",
                        List.of("객체지향", "자바"), 5, 20, joysun,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(14),
                        RoomClassification.BACKEND, RoomStatus.OPEN));

        // 실제 미션을 걸어놓은 방
        Room room5 = roomRepository.save(
                new Room("자바 크리스마스", "크리스마스가 얼마 남지 않았어요~", 1,
                        "https://github.com/youngsu5582/github-api-test",
                        "data:image/jpeg;base64,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",
                        List.of("클린코드", "자바"), 6, 20, joysun,
                        LocalDateTime.now()
                                .minusHours(1),
                        LocalDateTime.now()
                                .plusDays(14),
                        RoomClassification.BACKEND, RoomStatus.OPEN));

        List<Participation> room5Participates = participateRoom(room5, List.of(pororo, ash, cho, movin, ten, dar));
        saveExtraRooms(dar);

        //room1 에 참여한 참여자들
        List<Participation> room1Participates = participateRoom(room1, List.of(pororo, ash, cho, movin, ten, dar));

        //room2 에 참여한 참여자들
        List<Participation> room2Participates = participateRoom(room2, List.of(ash, cho, joysun, movin, ten, dar));

        //room3 에 참여한 참여자들
        List<Participation> room3Participates = participateRoom(room3, List.of(pororo, ash, joysun, movin, ten, dar));

        //room4 에 참여한 참여자들
        List<Participation> room4Participates = participateRoom(room4, List.of(ash, pororo, movin, ten, dar, cho));

        //room1 에서 작성된 매칭 & 피드백
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), pororo, ash, "https://github.com/example/java-racingcar/pull/2"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), pororo, cho, "https://github.com/example/java-racingcar/pull/3"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ash, cho, "https://github.com/example/java-racingcar/pull/3"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ash, movin, "https://github.com/example/java-racingcar/pull/4"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), cho, movin, "https://github.com/example/java-racingcar/pull/4"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), cho, ten, "https://github.com/example/java-racingcar/pull/5"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), movin, ten, "https://github.com/example/java-racingcar/pull/5"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), movin, dar, "https://github.com/example/java-racingcar/pull/2"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ten, pororo, "https://github.com/example/java-racingcar/pull/1"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ten, dar, "https://github.com/example/java-racingcar/pull/2"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), dar, pororo, "https://github.com/example/java-racingcar/pull/1"));
        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), dar, ash, "https://github.com/example/java-racingcar/pull/2"));

        //room2 모두가 리뷰 완료만 되어있는 상태
        matchingAndReviewComplete(room2Participates, room2);

        //room3 에서 매칭되고 모두 피드백 되어있는 상태
        matchingAndReview(room3Participates, room3);

        //room4 방이 열려있고 리뷰 완료만 되어있는 상태
        matchingAndReviewComplete(room4Participates, room4);

        //프로필
//        profileRepository.save(new corea.member.domain.Profile(1, 2, 3, 4.5f, 6.7f));
//        profileRepository.save(new corea.member.domain.Profile(2, 3, 4, 5.6f, 7.8f));
//        profileRepository.save(new corea.member.domain.Profile(3, 4, 5, 6.7f, 8.9f));
//        profileRepository.save(new corea.member.domain.Profile(4, 5, 6, 7.8f, 9.10f));
//        profileRepository.save(new corea.member.domain.Profile(5, 6, 7, 8.9f, 10.11f));
//        profileRepository.save(new corea.member.domain.Profile(6, 7, 8, 9.10f, 11.12f));
//        profileRepository.save(new corea.member.domain.Profile(7, 8, 9, 10.11f, 12.13f));
    }

    private List<Participation> participateRoom(Room room, List<Member> members) {
        return participationRepository.saveAll(members.stream()
                .map(member -> new Participation(room, member.getId(), member.getGithubUserId()))
                .toList());
    }

    private void matchingAndReview(List<Participation> participations, Room room) {
        List<MatchResult> matchResults = matchingStrategy.matchPairs(participations, room.getMatchingSize())
                .stream()
                .map(pair -> MatchResult.of(room.getId(), pair, ""))
                .toList();

        matchResults.forEach(this::reviewSocialAndDevelopFeedback);
    }

    private void matchingAndReviewComplete(List<Participation> participations, Room room) {
        List<MatchResult> matchResults = matchingStrategy.matchPairs(participations, room.getMatchingSize())
                .stream()
                .map(pair -> MatchResult.of(room.getId(), pair, ""))
                .toList();

        matchResults.forEach(MatchResult::reviewComplete);
        matchResultRepository.saveAll(matchResults);
    }

    private void reviewSocialAndDevelopFeedback(MatchResult matchResult) {
        socialFeedbackRepository.save(new SocialFeedback(matchResult.getRoomId(), matchResult.getReviewee(), matchResult.getReviewer(), 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        developFeedbackRepository.save(new DevelopFeedback(matchResult.getRoomId(), matchResult.getReviewer(), matchResult.getReviewee(), 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 3));
        matchResult.reviewComplete();
        matchResult.reviewerCompleteFeedback();
        matchResult.revieweeCompleteFeedback();
        matchResultRepository.save(matchResult);
    }

    private void saveExtraRooms(Member member) {
        roomRepository.save(
                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 3,
                        "https://github.com/example/kotlin-baseball",
                        "https://static.ebs.co.kr/images/public/lectures/2014/06/19/10/bhpImg/44deb98d-1c50-4073-9bd7-2c2c28d65f9e.jpg",
                        List.of("코틀린"), 1, 20, member,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(14),
                        RoomClassification.ANDROID, RoomStatus.OPEN));
        roomRepository.save(
                new Room("자바스크립트 크리스마스", "진짜 요구사항대로 구현하기!", 3,
                        "https://github.com/example/javascript-christmas",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242723588618_dphGgSgOAp.jpg",
                        List.of("구현", "자바스크립트"), 1, 20, member,
                        LocalDateTime.now()
                                .plusDays(5),
                        LocalDateTime.now()
                                .plusDays(19),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 6", "방 설명 6", 3,
                        "https://github.com/example/java-racingcar",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242723572507_BOtiBfIuyL.jpg",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 7", "방 설명 7", 3,
                        "https://github.com/example/java-racingcar",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242723212878_bxr2reBk9w.jpg",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));

        roomRepository.save(
                new Room("방 제목 8", "방 설명 8", 3,
                        "https://github.com/example/java-racingcar",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242723602807_VcCnrrJwzW.jpg",
                        List.of("TDD", "클린코드"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 9", "방 설명 9", 3,
                        "https://github.com/example/java-racingcar",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242723549377_49L83YjvJL.jpg",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.ANDROID, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 10", "방 설명 10", 3,
                        "https://github.com/example/java-racingcar",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242723513396_kp9fgpCfTO.jpg",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://static.ebs.co.kr/images/ebs/WAS-HOME/portal/upload/img/programinfo/person/per/1242724730403_Qkb1tVuekp.jpg",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));

        // 방 모집 완료
        roomRepository.save(
                new Room("방 제목 12", "방 설명 12", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 13", "방 설명 13", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 14", "방 설명 14", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 15", "방 설명 15", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 16", "방 설명 16", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 17", "방 설명 17", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 18", "방 설명 18", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 19", "방 설명 19", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 20", "방 설명 20", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 21", "방 설명 21", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member,
                        LocalDateTime.now()
                                .plusDays(3),
                        LocalDateTime.now()
                                .plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
    }

    private void initialize() {
        jdbcTemplate.execute("DELETE FROM login_info;");
        jdbcTemplate.execute("DELETE FROM match_result;");
        jdbcTemplate.execute("DELETE FROM member;");
        jdbcTemplate.execute("DELETE FROM ranking;");
        jdbcTemplate.execute("DELETE FROM room;");
        jdbcTemplate.execute("DELETE FROM profile;");
        jdbcTemplate.execute("DELETE FROM participation;");
        jdbcTemplate.execute("DELETE FROM develop_feedback;");
        jdbcTemplate.execute("DELETE FROM social_feedback;");
    }
}
