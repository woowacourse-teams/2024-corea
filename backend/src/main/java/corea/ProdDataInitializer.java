package corea;

import corea.evaluation.domain.EvaluateClassification;
import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.dto.DevelopFeedbackRequest;
import corea.feedback.dto.SocialFeedbackRequest;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.feedback.service.DevelopFeedbackService;
import corea.feedback.service.SocialFeedbackService;
import corea.matching.domain.MatchResult;
import corea.matching.domain.MatchingStrategy;
import corea.matching.repository.MatchResultRepository;
import corea.matching.service.MatchingService;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.ranking.domain.Ranking;
import corea.ranking.repository.RankingRepository;
import corea.review.service.ReviewService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static corea.feedback.domain.FeedbackKeyword.*;

@Profile("prod")
@Component
@Transactional
@RequiredArgsConstructor
public class ProdDataInitializer implements ApplicationRunner {


    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final MatchResultRepository matchResultRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;
    private final MatchingStrategy matchingStrategy;
    private final JdbcTemplate jdbcTemplate;
    private final RankingRepository rankingRepository;
    private final DevelopFeedbackService developFeedbackService;
    private final MatchingService matchingService;
    private final ReviewService reviewService;
    private final SocialFeedbackService socialFeedbackService;

    @Override
    public void run(ApplicationArguments args) {
        initialize();
        Member pororo = memberRepository.save(
                new Member("jcoding-play", "https://avatars.githubusercontent.com/u/119468757?v=4", "조경찬",
                        "pororo@email.com", true, "https://github.com/jcoding-play", "119468757"));
        Member ash = memberRepository.save(
                new Member("ashsty", "https://avatars.githubusercontent.com/u/77227961?v=4", "박민아",
                        "ash@email.com", false, "https://github.com/ashsty", "77227961"));
        Member joysun = memberRepository.save(
                new Member("youngsu5582", "https://avatars.githubusercontent.com/u/98307410?v=4", "이영수",
                        "joysun@email.com", false, "https://github.com/youngsu5582", "98307410"));
        Member movin = memberRepository.save(
                new Member("hjk0761", "https://avatars.githubusercontent.com/u/80106238?s=96&v=4", "김현중",
                        "movin@email.com", true, "https://github.com/hjk0761", "80106238"));
        Member ten = memberRepository.save(
                new Member("chlwlstlf", "https://avatars.githubusercontent.com/u/63334368?v=4", "최진실",
                        "tenten@email.com", true, "https://github.com/chlwlstlf", "63334368"));
        Member cho = memberRepository.save(
                new Member("00kang", "https://avatars.githubusercontent.com/u/70834044?v=4", "강다빈",
                        "choco@email.com", true, "https://github.com/00kang", "70834044"));
        Member dar = memberRepository.save(
                new Member("pp449", "https://avatars.githubusercontent.com/u/71641127?v=4", "이상엽",
                        "darr@email.com", true, "https://github.com/pp449", "71641127"));

        Member pobi = memberRepository.save(
                new Member("pobi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS88cehou499TNUfc41Iq8LouW5Ys_aQnQB6w&s", "포비",
                        "pobi@email.com", true, "12345678"));


        // 이미 모집 완료되어 매칭까지 진행된 방
        Room room1 = roomRepository.save(
                new Room("자바 레이싱 카 - 자바 클린코드", "자바를 처음 해본다면? 자바에서 클린 코드가 궁금하다면? final 이 뭐지. stream 이 뭐지?? 이 방은 자바에서 코드를 잘 작성하는 법을 같이 완성해나갈거에요.", 2,
                        "https://github.com/chlwlstlf/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("자바", "클린코드"), 6, 30, pobi,
                        LocalDateTime.now()
                                .minusDays(7),
                        LocalDateTime.now()
                                .minusDays(1),
                        RoomClassification.BACKEND, RoomStatus.CLOSED));

//         ash 기준 방장인 방
        Room room2 = roomRepository.save(
                new Room("자바 체스 - TDD", "애쉬와 함께하는 TDD", 3,
                        "https://github.com/woowacourse/java-chess",
                        "https://img.hankyung.com/photo/202306/AD.33633424.1.jpg",
                        List.of("TDD", "클린코드", "자바"), 6, 30, pobi,
                        LocalDateTime.now()
                                .minusDays(17),
                        LocalDateTime.now()
                                .minusDays(3),
                        RoomClassification.BACKEND, RoomStatus.CLOSED));

        Room room3 = roomRepository.save(
                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 2,
                        "https://github.com/woowacourse-precourse/kotlin-baseball-6",
                        "https://media.istockphoto.com/id/1471217278/ko/%EC%82%AC%EC%A7%84/%EC%95%BC%EA%B5%AC-%EA%B2%BD%EA%B8%B0%EC%9E%A5-%EA%B2%BD%EA%B8%B0%EC%9E%A5%EC%9D%98-%EC%9E%94%EB%94%94%EC%97%90-%EC%95%BC%EA%B5%AC-%EA%B3%B5.jpg?s=612x612&w=0&k=20&c=2comykS41HVEZgI5l9nPUVh4kmT-oxAOKmgHGAgK2aM=",
                        List.of("코틀린"), 6, 20, pobi,
                        LocalDateTime.now()
                                .minusDays(1),
                        LocalDateTime.now()
                                .plusDays(4),
                        RoomClassification.ANDROID, RoomStatus.OPENED));

        // 애쉬가 참여 가능한 방들
        Room room4 = roomRepository.save(
                new Room("자바 로또 - 객체지향", "객체지향 한 접시", 2,
                        "https://github.com/woowacourse-precourse/java-lotto-6",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYqN2oNe2BBVwm_lxJ6BbWS13Mkb9lmohGUw&s",
                        List.of("객체지향", "자바"), 5, 20, pobi,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(14),
                        RoomClassification.BACKEND, RoomStatus.OPENED));

        createRealRoom(pobi,List.of(ash, pororo, movin, ten, dar, cho));
//        saveExtraRooms(dar);

        //room3 에 참여한 참여자들
        //room4 에 참여한 참여자들
        List<Participation> room4Participates = participateRoom(room4.getId(), List.of(ash, pororo, movin, ten, dar, cho));

        //room1 에서 작성된 매칭 & 피드백
        List<MatchResult> matchResults1 = matchingAndReviewComplete(participateRoom(room1.getId(), List.of(joysun, ash, cho, movin, ten, dar)), room1);
        matchResults1.stream()
                .forEach(matchResult -> developFeedbackService.create(room1.getId(),
                        matchResult.getReviewer()
                                .getId(), randomDevelopFeedbackRequest(matchResult.getReviewee())));
        matchResults1.stream()
                .forEach(matchResult -> socialFeedbackService.create(room1.getId(),
                        matchResult.getReviewee()
                                .getId(), randomSocialFeedbackRequest(matchResult.getReviewer())));

        List<MatchResult> matchResults2 = matchingAndReviewComplete(participateRoom(room2.getId(), List.of(ash, cho, joysun, movin, ten, dar)), room2);
        matchResults2.stream()
                .forEach(matchResult -> developFeedbackService.create(room2.getId(),
                        matchResult.getReviewer()
                                .getId(), randomDevelopFeedbackRequest(matchResult.getReviewee())));
        matchResults2.stream()
                .forEach(matchResult -> socialFeedbackService.create(room2.getId(),
                        matchResult.getReviewee()
                                .getId(), randomSocialFeedbackRequest(matchResult.getReviewer())));

        List<MatchResult> matchResults3 = matchingAndReviewComplete(participateRoom(room3.getId(), List.of(ash, cho, joysun, movin, ten, dar)), room3);


        rankingRepository.save(new Ranking(pororo, 1, 5f, LocalDate.of(2024, 8, 22), EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(movin, 2, 4f, LocalDate.of(2024, 8, 22), EvaluateClassification.REVIEW));
        rankingRepository.save(new Ranking(ten, 3, 3f, LocalDate.of(2024, 8, 22), EvaluateClassification.REVIEW));

        rankingRepository.save(new Ranking(cho, 1, 5f, LocalDate.of(2024, 8, 22), EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(ash, 2, 4f, LocalDate.of(2024, 8, 22), EvaluateClassification.ANDROID));
        rankingRepository.save(new Ranking(dar, 3, 3f, LocalDate.of(2024, 8, 22), EvaluateClassification.ANDROID));

        rankingRepository.save(new Ranking(joysun, 1, 5f, LocalDate.of(2024, 8, 22), EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(pororo, 2, 4f, LocalDate.of(2024, 8, 22), EvaluateClassification.BACKEND));
        rankingRepository.save(new Ranking(movin, 3, 3f, LocalDate.of(2024, 8, 22), EvaluateClassification.BACKEND));

        rankingRepository.save(new Ranking(ten, 1, 5f, LocalDate.of(2024, 8, 22), EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(cho, 2, 4f, LocalDate.of(2024, 8, 22), EvaluateClassification.FRONTEND));
        rankingRepository.save(new Ranking(ash, 3, 3f, LocalDate.of(2024, 8, 22), EvaluateClassification.FRONTEND));
    }

    private DevelopFeedbackRequest randomDevelopFeedbackRequest(Member reviewee) {
        List<DevelopFeedbackRequest> requests = List.of(
                new DevelopFeedbackRequest(reviewee.getId(), 4,
                        List.of("코드를 이해하기 쉬웠어요", "방의 목적에 맞게 코드를 잘 작성했어요"),
                        "처음 자바를 접해봤다고 했는데 생각보다 매우 잘 구성되어 있는 코드였습니다!", 2),

                new DevelopFeedbackRequest(reviewee.getId(), 1,
                        List.of("방의 목적에 맞게 코드를 작성하지 않았어요", "응답 속도가 느렸어요"),
                        "의도에 맞지 않고, 솔직히 코드적으로 신경 쓰지 않은 부분이 많이 보였습니다.", 0),

                new DevelopFeedbackRequest(reviewee.getId(), 3,
                        List.of("코드를 이해는 했어요", "응답 속도가 적당했어요"),
                        "신경 쓴 부분이 많이 보이고, 아직 부족한 부분들이 보인거 같습니다. PR 피드백에 남긴걸 기반으로 성장 하면 좋겠습니다!", 1)
        );
        return requests.get(new Random().nextInt(requests.size()));
    }

    private SocialFeedbackRequest randomSocialFeedbackRequest(Member reviewer) {
        List<SocialFeedbackRequest> requests = List.of(
                new SocialFeedbackRequest(reviewer.getId(), 5, List.of("친절했어요", "리뷰 속도가 빨랐어요"),
                        "코드적으로 미흡했는데 불만 없이 잘 리뷰해주시고, 말투가 친절해서 궁금한 점도 물어볼 수 있었던 거 같습니다."),
                new SocialFeedbackRequest(reviewer.getId(), 3, List.of("리뷰 속도가 적당했어요", "도움이 되었어요"),
                        "마지막 날에 리뷰해줘서 아쉬웠지만, 피드백 해준 내용은 나쁘지 않았습니다."),
                new SocialFeedbackRequest(reviewer.getId(), 1, List.of("도움이 되지 않았어요", "리뷰 속도가 느렸어요"),
                        "그냥 성의 없었음.")
        );
        return requests.get(new Random().nextInt(requests.size()));
    }

    private List<Participation> participateRoom(long roomId, List<Member> members) {
        return participationRepository.saveAll(members.stream()
                .map(member -> new Participation(roomId, member.getId(), member.getGithubUserId()))
                .toList());
    }

    private void matchingAndReview(List<Participation> participations, Room room) {
        List<MatchResult> matchResults = matchingStrategy.matchPairs(participations, room.getMatchingSize())
                .stream()
                .map(pair -> MatchResult.of(room.getId(), pair, ""))
                .toList();

        matchResults.forEach(this::reviewSocialAndDevelopFeedback);
    }

    private List<MatchResult> matchingAndReviewComplete(List<Participation> participations, Room room) {
        List<MatchResult> matchResults = matchResultRepository.saveAll(matchingStrategy.matchPairs(participations, room.getMatchingSize())
                .stream()
                .map(pair -> MatchResult.of(room.getId(), pair, ""))
                .toList());

        matchResults.forEach(matchResult -> reviewService.review(room.getId(), matchResult.getReviewer()
                .getId(), matchResult.getReviewee()
                .getId()));
        return matchResultRepository.saveAll(matchResults);
    }

    private void createRealRoom(Member manager,List<Member> members) {
        Room javaSample1 =
                new Room("자바 레이싱 카 - 자바 클린코드", "자바를 처음 해본다면? 자바에서 클린 코드가 궁금하다면? final 이 뭐지. 읽기 좋은 코드는 뭘까? 이 방은 자바에서 코드를 잘 작성하는 법을 같이 완성해나갈거에요.", 2,
                        "https://github.com/chlwlstlf/java-baseball-6",
                        "https://img.khan.co.kr/news/2020/09/28/l_2020092801003296100265681.jpg",
                        List.of("자바", "클린코드"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.BACKEND, RoomStatus.OPENED);

        Room javaSample2 =
                new Room("자바 레이싱 카 - 모던 자바 인 액션", "자바에 대해 기초적인 걸 알고 있다면? 자바에서도 함수형이 가능한 걸 알고 있나요? Predicate,Optional,Stream 등을 함께 학습해서 도입해볼거에요.", 2,
                        "https://github.com/chlwlstlf/java-baseball-6",
                        "https://img.khan.co.kr/news/2020/09/28/l_2020092801003296100265681.jpg",
                        List.of("자바", "MVC", "디자인 패턴"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.BACKEND, RoomStatus.OPENED);

        Room javaSample3 =
                new Room("자바 레이싱 카 - MVC 패턴", "자바로 코드를 작성하는 것에 기본기가 있다면? 디자인 패턴에 대해서 같이 학습해볼까요? M(Model) - V(View) - C(Controller) 는 왜 쓰고, 어떻게 쓸까요? 같이 디자인 패턴에 대해 학습하고, 사용해봐요", 2,
                        "https://github.com/chlwlstlf/java-baseball-6",
                        "https://d3jn14jkdoqvmm.cloudfront.net/wp/wp-content/uploads/2021/11/10185835/%E1%84%85%E1%85%A1%E1%86%B7%E1%84%87%E1%85%A9%E1%84%85%E1%85%B3%E1%84%80%E1%85%B5%E1%84%82%E1%85%B5%E1%84%80%E1%85%A1-%E1%84%8B%E1%85%A3%E1%86%BC%E1%84%87%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B3%E1%86%AF-%E1%84%89%E1%85%B5%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%92%E1%85%A1%E1%86%AB-%E1%84%8B%E1%85%B5%E1%84%8B%E1%85%B200-evpost.jpg",
                        List.of("자바", "MVC", "디자인 패턴"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.BACKEND, RoomStatus.OPENED);

        // 자바스크립트 방들
        Room javascriptSample1 =
                new Room("자바스크립트 레이싱 카 - class란?", "자바스크립트를 처음 해본다면? 자바스크립트에서 클린 코드가 궁금하다면? class가 뭐지. 읽기 좋은 코드는 뭘까? 이 방은 자바스크립트에서 코드를 잘 작성하는 법을 같이 완성해나갈거에요.", 2,
                        "https://github.com/youngsu5582/javascript-baseball-6",
                        "https://i.namu.wiki/i/eNBgFDMtRhTN3_oAoALhqq7bJbk3rfVNFzzqvkiOC-RYRuHyIulpjM-d6W851k-nS4i65Y-SPkdN2_3hauIVoyFAdRCsZEKCcxOOw_f8b_IseAo_kCg9NFoCDiwaouWmjz8pwwlD5ci5ZxQ2mFJuyQ.webp",
                        List.of("자바스크립트", "클린코드"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.FRONTEND, RoomStatus.OPENED);

        Room javascriptSample2 =
                new Room("자바스크립트 레이싱 카 - private field", "자바스크립트에 대해 기초적인 걸 알고 있다면? 객체의 상태를 외부에서 직접 접근하지 않게 하려면? getter와 setter의 사용을 왜 지양해야 할까요?", 2,
                        "https://github.com/youngsu5582/javascript-baseball-6",
                        "https://i.namu.wiki/i/eNBgFDMtRhTN3_oAoALhqq7bJbk3rfVNFzzqvkiOC-RYRuHyIulpjM-d6W851k-nS4i65Y-SPkdN2_3hauIVoyFAdRCsZEKCcxOOw_f8b_IseAo_kCg9NFoCDiwaouWmjz8pwwlD5ci5ZxQ2mFJuyQ.webp",
                        List.of("자바스크립트", "private field"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.FRONTEND, RoomStatus.OPENED);

        Room javascriptSample3 = roomRepository.save(
                new Room("자바스크립트 레이싱 카 - 테스트 코드", "자바스크립트로 코드를 작성하는 것에 기본기가 있다면? 테스트 코드에 대해서 같이 학습해볼까요? 모든 코드가 테스트가 쉬울까요? 반환값이 랜덤이라면.?", 2,
                        "https://github.com/youngsu5582/javascript-baseball-6",
                        "https://i.namu.wiki/i/eNBgFDMtRhTN3_oAoALhqq7bJbk3rfVNFzzqvkiOC-RYRuHyIulpjM-d6W851k-nS4i65Y-SPkdN2_3hauIVoyFAdRCsZEKCcxOOw_f8b_IseAo_kCg9NFoCDiwaouWmjz8pwwlD5ci5ZxQ2mFJuyQ.webp",
                        List.of("자바스크립트", "테스트", "moking"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));

        // 코틀린 방들
        Room kotlinSample1 =
                new Room("코틀린 안드로이드 - 코틀린 기초 다지기", "안드로이드 개발을 처음 시작하셨나요? 코틀린 언어를 처음 접하시나요? 이 방에서는 코틀린의 기초 문법과 안드로이드에서의 사용법을 함께 학습하며, 안드로이드 코틀린 개발의 기초를 튼튼히 다져볼 거에요.", 2,
                        "https://github.com/youngsu5582/kotlin-baseball-6",
                        "https://i.namu.wiki/i/ik_52FkuiFbfHV6Wco6ubGxKBYhiXLtmb8z7sONY11keATSEl2LByMU0UmcJTbFyBADlg07FGTwl32Y7mOpxlQ.webp",
                        List.of("자바스크립트", "MVC", "디자인 패턴"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.ANDROID, RoomStatus.OPENED);

        Room kotlinSample2 =
                new Room("코틀린 안드로이드 - 함수형 프로그래밍", "코틀린의 기본적인 문법을 알고 있나요? 코틀린에서의 함수형 프로그래밍 개념을 배워보고, 실전에 도입해볼까요? 이 방에서는 Lambda, Higher-order functions 등을 학습하며 코틀린을 더 효율적으로 사용하는 방법을 익혀봅니다.", 2,
                        "https://github.com/youngsu5582/kotlin-baseball-6",
                        "https://i.namu.wiki/i/ik_52FkuiFbfHV6Wco6ubGxKBYhiXLtmb8z7sONY11keATSEl2LByMU0UmcJTbFyBADlg07FGTwl32Y7mOpxlQ.webp",
                        List.of("자바스크립트", "MVC", "디자인 패턴"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.ANDROID, RoomStatus.OPENED);

        Room kotlinSample3 =
                new Room("코틀린 안드로이드 - MVVM 패턴", "안드로이드 개발 경험이 있으신가요? 코틀린으로 MVVM 패턴을 사용해보고 싶으신가요? Model-View-ViewModel 패턴의 이해와 적용 방법을 함께 학습하며, 더 나은 안드로이드 코드를 작성해봅시다.", 2,
                        "https://github.com/youngsu5582/kotlin-baseball-6",
                        "https://i.namu.wiki/i/ik_52FkuiFbfHV6Wco6ubGxKBYhiXLtmb8z7sONY11keATSEl2LByMU0UmcJTbFyBADlg07FGTwl32Y7mOpxlQ.webp",
                        List.of("자바스크립트", "MVC", "디자인 패턴"), 1, 40, manager,
                        LocalDateTime.now()
                                .plusDays(2),
                        LocalDateTime.now()
                                .plusDays(7),
                        RoomClassification.ANDROID, RoomStatus.OPENED);

        // 각 언어별 방들 3배로 복제하여 저장
        List<Room> rooms = List.of(
                javaSample1, javaSample2, javaSample3,
                javascriptSample1, javascriptSample2, javascriptSample3,
                kotlinSample1, kotlinSample2, kotlinSample3
        );

        for (int i = 0; i < 9; i++) {
            for (Room room : rooms) {
                Room room1 = roomRepository.save(new Room(
                        room.getTitle(),
                        room.getContent(),
                        room.getMatchingSize(),
                        room.getRepositoryLink(),
                        room.getThumbnailLink(),
                        room.getKeyword(),
                        room.getCurrentParticipantsSize(),
                        room.getLimitedParticipantsSize(),
                        manager,
                        room.getRecruitmentDeadline(),
                        room.getReviewDeadline(),
                        room.getClassification(),
                        room.getStatus()
                ));
                participateRoom(room1.getId(), members);
            }
        }
    }

    private void reviewSocialAndDevelopFeedback(MatchResult matchResult) {
        socialFeedbackRepository.save(new SocialFeedback(matchResult.getRoomId(), matchResult.getReviewee(), matchResult.getReviewer(), 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요! 모르는 내용도 있었는데, "));
        developFeedbackRepository.save(new DevelopFeedback(matchResult.getRoomId(), matchResult.getReviewer(), matchResult.getReviewee(), 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 3));
        matchResult.reviewComplete();
        matchResult.reviewerCompleteFeedback();
        matchResult.revieweeCompleteFeedback();
        matchResultRepository.save(matchResult);
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

        // AUTO_INCREMENT 재설정
        jdbcTemplate.execute("ALTER TABLE login_info AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE match_result AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE member AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE room AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE ranking AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE profile AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE participation AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE develop_feedback AUTO_INCREMENT 1;");
        jdbcTemplate.execute("ALTER TABLE social_feedback AUTO_INCREMENT 1;");
    }
}
