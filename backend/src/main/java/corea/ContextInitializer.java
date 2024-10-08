package corea;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.domain.MatchingStrategy;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberRole;
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

@Profile({"dev", "local"})
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
//        initialize();
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

        Room r1 = roomRepository.save(
                new Room("주문", "배달 주문을 받아보자", 2,
                        "https://github.com/develup-mission/java-order",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-order.webp",
                        List.of("JAVA", "객체지향", "클린코드"), 4, 10,
                        pororo,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(5),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        List<Participation> r1Participations = List.of(
                new Participation(r1, pororo, MemberRole.BOTH),
                new Participation(r1, joysun, MemberRole.BOTH),
                new Participation(r1, movin, MemberRole.BOTH),
                new Participation(r1, ash, MemberRole.BOTH)
        );
        participationRepository.saveAll(r1Participations);

        Room r2 = roomRepository.save(
                new Room("숫자 맞추기 게임", "숫자를 맞춰보자", 2,
                        "https://github.com/develup-mission/java-guessing-number",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-guessing-number.webp",
                        List.of("JAVA", "TDD", "클린코드"), 3, 15,
                        ash,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(6),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        List<Participation> r2Participations = List.of(
                new Participation(r2, pororo, MemberRole.BOTH),
                new Participation(r2, joysun, MemberRole.BOTH),
                new Participation(r2, ash, MemberRole.BOTH)
        );
        participationRepository.saveAll(r2Participations);

        Room r3 = roomRepository.save(
                new Room("미로 탈출", "미노타우로스를 피해 미로에서 탈출하세요!", 2,
                        "https://github.com/develup-mission/java-maze",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-maze.webp",
                        List.of("MVC", "자바", "디자인패턴"), 5, 10,
                        joysun,
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(3),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        List<Participation> r3Participations = List.of(
                new Participation(r3, pororo, MemberRole.BOTH),
                new Participation(r3, joysun, MemberRole.BOTH),
                new Participation(r3, movin, MemberRole.BOTH),
                new Participation(r3, ash, MemberRole.BOTH),
                new Participation(r3, cho, MemberRole.BOTH)
        );
        participationRepository.saveAll(r3Participations);

        Room r4 = roomRepository.save(
                new Room("엘리베이터 시뮬레이션", "엘리베이터를 만들어봐요.", 2,
                        "https://github.com/develup-mission/java-elevator",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-elevator.webp",
                        List.of("TDD", "클린코드", "자바"), 6, 20,
                        movin,
                        LocalDateTime.now().plusDays(4),
                        LocalDateTime.now().plusDays(7),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        List<Participation> r4Participations = List.of(
                new Participation(r4, pororo, MemberRole.BOTH),
                new Participation(r4, joysun, MemberRole.BOTH),
                new Participation(r4, movin, MemberRole.BOTH),
                new Participation(r4, ash, MemberRole.BOTH),
                new Participation(r4, cho, MemberRole.BOTH),
                new Participation(r4, ten, MemberRole.BOTH)
        );
        participationRepository.saveAll(r4Participations);

        Room r5 = roomRepository.save(
                new Room("단어 퍼즐 게임", "단어의 퍼즐들을 맞춰주세요!", 2,
                        "https://github.com/develup-mission/java-word-puzzle",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-word-puzzle.webp",
                        List.of("JAVA", "객체지향", "클린코드"), 6, 25,
                        cho,
                        LocalDateTime.now().minusDays(2),
                        LocalDateTime.now().plusDays(2),
                        RoomClassification.BACKEND, RoomStatus.PROGRESS));
        List<Participation> r5Participations = List.of(
                new Participation(r5, pororo, MemberRole.BOTH),
                new Participation(r5, joysun, MemberRole.BOTH),
                new Participation(r5, movin, MemberRole.BOTH),
                new Participation(r5, ash, MemberRole.BOTH),
                new Participation(r5, cho, MemberRole.BOTH),
                new Participation(r5, ten, MemberRole.BOTH)
        );
        participationRepository.saveAll(r5Participations);
        matchingStrategy.matchPairs(r5Participations, 2)
                .stream()
                .map(pair -> MatchResult.of(r5.getId(), pair, ""))
                .forEach(matchResultRepository::save);

        Room r6 = roomRepository.save(
                new Room("리액트 회원가입/로그인 폼", "리액트 회원가입/로그인 폼을 만들어봐요.", 2,
                        "https://github.com/develup-mission/react-auth-form",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/react-auth-form.webp",
                        List.of("TYPESCRIPT", "REACT"), 7, 15,
                        ten,
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(3),
                        RoomClassification.FRONTEND, RoomStatus.PROGRESS));
        List<Participation> r6Participations = List.of(
                new Participation(r6, pororo, MemberRole.BOTH),
                new Participation(r6, joysun, MemberRole.BOTH),
                new Participation(r6, movin, MemberRole.BOTH),
                new Participation(r6, ash, MemberRole.BOTH),
                new Participation(r6, cho, MemberRole.BOTH),
                new Participation(r6, ten, MemberRole.BOTH),
                new Participation(r6, dar, MemberRole.BOTH)
        );
        participationRepository.saveAll(r6Participations);
        matchingStrategy.matchPairs(r6Participations, 2)
                .stream()
                .map(pair -> MatchResult.of(r6.getId(), pair, ""))
                .forEach(matchResultRepository::save);

        Room r7 = roomRepository.save(
                new Room("코틀린 레이싱 카", "자동차 게임을 만들어봐요.", 2,
                        "https://github.com/woowacourse/kotlin-racingcar",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtiY6mms9QRp80a1IGY1oqnTMmVYe7VmUasA&s",
                        List.of("코틀린"), 7, 30,
                        dar,
                        LocalDateTime.now().minusDays(7),
                        LocalDateTime.now().minusDays(1),
                        RoomClassification.ANDROID, RoomStatus.CLOSE));
        List<Participation> r7Participations = List.of(
                new Participation(r7, pororo, MemberRole.BOTH),
                new Participation(r7, joysun, MemberRole.BOTH),
                new Participation(r7, movin, MemberRole.BOTH),
                new Participation(r7, ash, MemberRole.BOTH),
                new Participation(r7, cho, MemberRole.BOTH),
                new Participation(r7, ten, MemberRole.BOTH),
                new Participation(r7, dar, MemberRole.BOTH)
        );
        participationRepository.saveAll(r7Participations);
        List<MatchResult> r7MatchResults = matchingStrategy.matchPairs(r7Participations, 2)
                .stream()
                .map(pair -> MatchResult.of(r7.getId(), pair, ""))
                .toList();

        r7MatchResults.forEach(this::reviewSocialAndDevelopFeedback);

        Room r8 = roomRepository.save(
                new Room("자바 크리스마스", "크리스마스 프로모션", 2,
                        "https://github.com/woowacourse-precourse/java-christmas-6",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Christmas_Tree_and_Presents.jpg/1200px-Christmas_Tree_and_Presents.jpg",
                        List.of("클린코드", "자바", "TDD"), 6, 10,
                        pororo,
                        LocalDateTime.now().minusHours(1),
                        LocalDateTime.now().plusDays(14),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        List<Participation> r8Participations = List.of(
                new Participation(r8, pororo, MemberRole.BOTH),
                new Participation(r8, joysun, MemberRole.BOTH),
                new Participation(r8, movin, MemberRole.BOTH),
                new Participation(r8, ash, MemberRole.BOTH),
                new Participation(r8, cho, MemberRole.BOTH),
                new Participation(r8, ten, MemberRole.BOTH)
        );
        participationRepository.saveAll(r8Participations);
        List<MatchResult> r8MatchResults = matchingStrategy.matchPairs(r8Participations, 2)
                .stream()
                .map(pair -> MatchResult.of(r8.getId(), pair, ""))
                .toList();

        r8MatchResults.forEach(this::reviewSocialAndDevelopFeedback);

        roomRepository.save(
                new Room("주문", "배달 주문을 받아보자", 2,
                        "https://github.com/develup-mission/java-order",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-order.webp",
                        List.of("JAVA", "객체지향", "클린코드"), 4, 10,
                        pororo,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(5),
                        RoomClassification.BACKEND, RoomStatus.OPEN));

        roomRepository.save(
                new Room("주문", "배달 주문을 받아보자", 2,
                        "https://github.com/develup-mission/java-order",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-order.webp",
                        List.of("JAVA", "객체지향", "클린코드"), 4, 10,
                        pororo,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(5),
                        RoomClassification.BACKEND, RoomStatus.OPEN));

        roomRepository.save(
                new Room("주문", "배달 주문을 받아보자", 2,
                        "https://github.com/develup-mission/java-order",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-order.webp",
                        List.of("JAVA", "객체지향", "클린코드"), 4, 10,
                        pororo,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(5),
                        RoomClassification.BACKEND, RoomStatus.OPEN));

        roomRepository.save(
                new Room("주문", "배달 주문을 받아보자", 2,
                        "https://github.com/develup-mission/java-order",
                        "https://raw.githubusercontent.com/develup-mission/docs/main/image/java-order.webp",
                        List.of("JAVA", "객체지향", "클린코드"), 4, 10,
                        pororo,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(5),
                        RoomClassification.BACKEND, RoomStatus.OPEN));

//        // 이미 모집 완료되어 매칭까지 진행된 방
//        Room room1 = roomRepository.save(
//                new Room("자바 레이싱 카 - MVC", "MVC 패턴을 아시나요?", 2,
//                        "https://github.com/woowacourse/java-racingcar",
//                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
//                        List.of("MVC", "자바", "디자인패턴"), 6, 30, pororo,
//                        LocalDateTime.now()
//                                .minusDays(7),
//                        LocalDateTime.now()
//                                .minusDays(1),
//                        RoomClassification.BACKEND, RoomStatus.CLOSE));
//
//        // ash 기준 방장인 방
//        Room room2 = roomRepository.save(
//                new Room("자바 체스 - TDD", "애쉬와 함께하는 TDD", 3,
//                        "https://github.com/woowacourse/java-chess",
//                        "https://i.namu.wiki/i/ZuKHR3uGK77VBnvkBO9f1XrAdne3sKlaNR_a-no5alODVhSxhbt4_BnUFPJ1zQMMzkNrvdvpEYvXVt2BBAhIuMpotu6H6ua-Ou5ps01yKD66rukqtW2sCdAuyYUSg_bSngRl6b-tlt5umrKxwd5olQ.webp",
//                        List.of("TDD", "클린코드", "자바"), 6, 30, ash,
//                        LocalDateTime.now()
//                                .plusDays(3),
//                        LocalDateTime.now()
//                                .plusDays(17),
//                        RoomClassification.BACKEND, RoomStatus.CLOSE));
//
//        // 애쉬가 참여했는데 끝난 방
//        Room room3 = roomRepository.save(
//                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 2,
//                        "https://github.com/woowacourse-precourse/java-baseball-6",
//                        "https://media.istockphoto.com/id/1471217278/photo/baseball-ball-in-a-grass-of-baseball-arena-stadium.jpg?s=612x612&w=0&k=20&c=OaKZFgu2C9P_rVQnGZYB31pf1YYMk6I5Qoe3ia5H7QM=",
//                        List.of("코틀린"), 6, 20, movin,
//                        LocalDateTime.now()
//                                .minusDays(14),
//                        LocalDateTime.now()
//                                .minusDays(2),
//                        RoomClassification.ANDROID, RoomStatus.CLOSE));
//
//        // 애쉬가 참여 가능한 방들
//        Room room4 = roomRepository.save(
//                new Room("자바 로또 - 객체지향", "객체지향 한 접시", 2,
//                        "https://github.com/woowacourse-precourse/java-lotto-6",
//                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYqN2oNe2BBVwm_lxJ6BbWS13Mkb9lmohGUw&s",
//                        List.of("객체지향", "자바"), 5, 20, joysun,
//                        LocalDateTime.now()
//                                .plusDays(2),
//                        LocalDateTime.now()
//                                .plusDays(14),
//                        RoomClassification.BACKEND, RoomStatus.OPEN));
//
//        // 실제 미션을 걸어놓은 방
//        Room room5 = roomRepository.save(
//                new Room("자바 크리스마스", "크리스마스가 얼마 남지 않았어요~", 1,
//                        "https://github.com/youngsu5582/github-api-test",
//                        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Christmas_Tree_and_Presents.jpg/1200px-Christmas_Tree_and_Presents.jpg",
//                        List.of("클린코드", "자바"), 6, 20, joysun,
//                        LocalDateTime.now()
//                                .minusHours(1),
//                        LocalDateTime.now()
//                                .plusDays(14),
//                        RoomClassification.BACKEND, RoomStatus.OPEN));
//
//        List<Participation> room5Participates = participateRoom(room5, List.of(pororo, ash, cho, movin, ten, dar));
//        saveExtraRooms(dar);
//
//        //room1 에 참여한 참여자들
//        List<Participation> room1Participates = participateRoom(room1, List.of(pororo, ash, cho, movin, ten, dar));
//
//        //room2 에 참여한 참여자들
//        List<Participation> room2Participates = participateRoom(room2, List.of(ash, cho, joysun, movin, ten, dar));
//
//        //room3 에 참여한 참여자들
//        List<Participation> room3Participates = participateRoom(room3, List.of(pororo, ash, joysun, movin, ten, dar));
//
//        //room4 에 참여한 참여자들
//        List<Participation> room4Participates = participateRoom(room4, List.of(ash, pororo, movin, ten, dar, cho));
//
//        //room1 에서 작성된 매칭 & 피드백
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), pororo, ash, "https://github.com/example/java-racingcar/pull/2"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), pororo, cho, "https://github.com/example/java-racingcar/pull/3"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ash, cho, "https://github.com/example/java-racingcar/pull/3"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ash, movin, "https://github.com/example/java-racingcar/pull/4"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), cho, movin, "https://github.com/example/java-racingcar/pull/4"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), cho, ten, "https://github.com/example/java-racingcar/pull/5"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), movin, ten, "https://github.com/example/java-racingcar/pull/5"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), movin, dar, "https://github.com/example/java-racingcar/pull/2"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ten, pororo, "https://github.com/example/java-racingcar/pull/1"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), ten, dar, "https://github.com/example/java-racingcar/pull/2"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), dar, pororo, "https://github.com/example/java-racingcar/pull/1"));
//        reviewSocialAndDevelopFeedback(new MatchResult(room1.getId(), dar, ash, "https://github.com/example/java-racingcar/pull/2"));
//
//        //room2 모두가 리뷰 완료만 되어있는 상태
//        matchingAndReviewComplete(room2Participates, room2);
//
//        //room3 에서 매칭되고 모두 피드백 되어있는 상태
//        matchingAndReview(room3Participates, room3);
//
//        //room4 방이 열려있고 리뷰 완료만 되어있는 상태
//        matchingAndReviewComplete(room4Participates, room4);

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
                .map(member -> new Participation(room, member, MemberRole.BOTH))
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
        socialFeedbackRepository.save(new SocialFeedback(matchResult.getRoomId(), matchResult.getReviewee(), matchResult.getReviewer(), 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투이고, 설명이 친절했어요! 기회가 된다면 한번 더 리뷰 받고 싶어요~"));
        developFeedbackRepository.save(new DevelopFeedback(matchResult.getRoomId(), matchResult.getReviewer(), matchResult.getReviewee(), 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "코드를 이해하기 쉬워 리뷰하기 편했어요!~", 3));
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

//    private void initialize() {
//        jdbcTemplate.execute("DELETE FROM login_info;");
//        jdbcTemplate.execute("DELETE FROM match_result;");
//        jdbcTemplate.execute("DELETE FROM member;");
//        jdbcTemplate.execute("DELETE FROM ranking;");
//        jdbcTemplate.execute("DELETE FROM room;");
//        jdbcTemplate.execute("DELETE FROM profile;");
//        jdbcTemplate.execute("DELETE FROM participation;");
//        jdbcTemplate.execute("DELETE FROM develop_feedback;");
//        jdbcTemplate.execute("DELETE FROM social_feedback;");
//    }
}
