package corea;

import corea.feedback.domain.DevelopFeedback;
import corea.feedback.domain.SocialFeedback;
import corea.feedback.repository.DevelopFeedbackRepository;
import corea.feedback.repository.SocialFeedbackRepository;
import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.profile.repository.ProfileRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static corea.feedback.domain.FeedbackKeyword.*;

@Profile("!test")
@Component
@Transactional
@RequiredArgsConstructor
public class DemoDataInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final MatchResultRepository matchResultRepository;
    private final DevelopFeedbackRepository developFeedbackRepository;
    private final SocialFeedbackRepository socialFeedbackRepository;
    private final ProfileRepository profileRepository;

    @Override
    public void run(ApplicationArguments args) {
        Member pororo = memberRepository.save(
                new Member("jcoding-play", "https://avatars.githubusercontent.com/u/119468757?v=4", "조경찬",
                        "pororo@email.com", true));
        Member ash = memberRepository.save(
                new Member("ashsty", "https://avatars.githubusercontent.com/u/77227961?v=4", "박민아",
                        "ash@email.com", false));
        Member joysun = memberRepository.save(
                new Member("youngsu5582", "https://avatars.githubusercontent.com/u/98307410?v=4", "이영수",
                        "joysun@email.com", false));
        Member movin = memberRepository.save(
                new Member("hjk0761", "https://avatars.githubusercontent.com/u/80106238?s=96&v=4", "김현중",
                        "movin@email.com", true));
        Member ten = memberRepository.save(
                new Member("chlwlstlf", "https://avatars.githubusercontent.com/u/63334368?v=4", "최진실",
                        "tenten@email.com", true));
        Member cho = memberRepository.save(
                new Member("00kang", "https://avatars.githubusercontent.com/u/70834044?v=4", "강다빈",
                        "choco@email.com", true));
        Member dar = memberRepository.save(
                new Member("pp449", "https://avatars.githubusercontent.com/u/71641127?v=4", "이상엽",
                        "darr@email.com", true));

        // 이미 모집 완료되어 매칭까지 진행된 방
        Room room1 = roomRepository.save(
                new Room("자바 레이싱 카 - MVC", "MVC 패턴을 아시나요?", 2,
                        "https://github.com/woowacourse/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("MVC", "자바", "디자인패턴"), 6, 30, pororo,
                        LocalDateTime.now().minusDays(7),
                        LocalDateTime.now().minusDays(1),
                        RoomClassification.BACKEND, RoomStatus.CLOSED));

        // ash 기준 방장인 방
        Room room2 = roomRepository.save(
                new Room("자바 체스 - TDD", "애쉬와 함께하는 TDD", 3,
                        "https://github.com/woowacourse/java-chess",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fnamu.wiki%2Fw%2F%25EC%25B2%25B4%25EC%258A%25A4&psig=AOvVaw05j3MCW1hohJcqp_erhVtK&ust=1723183640447000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIjO8rfd5IcDFQAAAAAdAAAAABAE",
                        List.of("TDD", "클린코드", "자바"), 6, 30, ash,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.BACKEND, RoomStatus.OPENED));

        // 애쉬가 참여했는데 끝난 방
        Room room3 = roomRepository.save(
                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 2,
                        "https://github.com/woowacourse-precourse/java-baseball-6",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fkr%2F%25EC%259D%25B4%25EB%25AF%25B8%25EC%25A7%2580%2F%25EC%2595%25BC%25EA%25B5%25AC&psig=AOvVaw2I1ENU-tsvDql-TFQyvEds&ust=1723188554258000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIiyheDv5IcDFQAAAAAdAAAAABAE",
                        List.of("코틀린"), 6, 20, movin,
                        LocalDateTime.now().minusDays(14),
                        LocalDateTime.now().minusDays(2),
                        RoomClassification.ANDROID, RoomStatus.CLOSED));

        // 애쉬가 참여 가능한 방들
        Room room4 = roomRepository.save(
                new Room("자바 로또 - 객체지향", "객체지향 한 접시", 2,
                        "https://github.com/woowacourse-precourse/java-lotto-6",
                        "https://play.google.com/store/apps/details?id=com.min.lotto&hl=ko",
                        List.of("객체지향", "자바"), 5, 20, joysun,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(14),
                        RoomClassification.BACKEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 3,
                        "https://github.com/example/kotlin-baseball",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fhome.ebs.co.kr%2Fpororo%2Fetc%2F1%2Fcast&psig=AOvVaw2wRFOE5sri_pL_-Y3lRIPk&ust=1723188607417000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIjv_Pfv5IcDFQAAAAAdAAAAABAE",
                        List.of("코틀린"), 1, 20, movin,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(14),
                        RoomClassification.ANDROID, RoomStatus.OPENED));
        roomRepository.save(
                new Room("자바스크립트 크리스마스", "진짜 요구사항대로 구현하기!", 3,
                        "https://github.com/example/javascript-christmas",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fhome.ebs.co.kr%2Fpororo%2Fetc%2F1%2Fcast&psig=AOvVaw03Gxi-NexSw38NOiZFgqXp&ust=1723188624984000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDnrYDw5IcDFQAAAAAdAAAAABAE",
                        List.of("구현", "자바스크립트"), 1, 20, ten,
                        LocalDateTime.now().plusDays(5),
                        LocalDateTime.now().plusDays(19),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 6", "방 설명 6", 3,
                        "https://github.com/example/java-racingcar",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fnamu.wiki%2Fw%2F%25ED%258F%25AC%25EB%25B9%2584%2528%25EB%25BD%2580%25EB%25A1%25B1%25EB%25BD%2580%25EB%25A1%25B1%2520%25EB%25BD%2580%25EB%25A1%259C%25EB%25A1%259C%2529&psig=AOvVaw0J3Yw1iEnjiUmaoVmRHq0y&ust=1723188649551000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCICEqozw5IcDFQAAAAAdAAAAABAE",
                        List.of("TDD"),
                        1, 20, cho,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 7", "방 설명 7", 3,
                        "https://github.com/example/java-racingcar",
                        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fhome.ebs.co.kr%2Fpororo%2Fetc%2F1%2Fcast&psig=AOvVaw0pGtBrO8HxwZvWmufvfmDC&ust=1723188646712000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCKiY5Jrw5IcDFQAAAAAdAAAAABAE",
                        List.of("TDD"),
                        1, 20, dar,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 8", "방 설명 8", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD", "클린코드"),
                        1, 20, pororo,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.BACKEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 9", "방 설명 9", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, joysun,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.ANDROID, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 10", "방 설명 10", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));

        // 방 모집 완료
        roomRepository.save(
                new Room("방 제목 12", "방 설명 12", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 13", "방 설명 13", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 14", "방 설명 14", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 15", "방 설명 15", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 16", "방 설명 16", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 17", "방 설명 17", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 18", "방 설명 18", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 19", "방 설명 19", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 20", "방 설명 20", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 21", "방 설명 21", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, ten,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));

        //room1 에 참여한 참여자들
        participationRepository.save(new Participation(room1.getId(), pororo.getId()));
        participationRepository.save(new Participation(room1.getId(), ash.getId()));
        participationRepository.save(new Participation(room1.getId(), joysun.getId()));
        participationRepository.save(new Participation(room1.getId(), movin.getId()));
        participationRepository.save(new Participation(room1.getId(), ten.getId()));
        participationRepository.save(new Participation(room1.getId(), dar.getId()));

        //room2 에 참여한 참여자들
        participationRepository.save(new Participation(room2.getId(), ash.getId()));
        participationRepository.save(new Participation(room2.getId(), cho.getId()));
        participationRepository.save(new Participation(room2.getId(), joysun.getId()));
        participationRepository.save(new Participation(room2.getId(), movin.getId()));
        participationRepository.save(new Participation(room2.getId(), ten.getId()));
        participationRepository.save(new Participation(room2.getId(), dar.getId()));

        //room3 에 참여한 참여자들
        participationRepository.save(new Participation(room3.getId(), pororo.getId()));
        participationRepository.save(new Participation(room3.getId(), ash.getId()));
        participationRepository.save(new Participation(room3.getId(), joysun.getId()));
        participationRepository.save(new Participation(room3.getId(), movin.getId()));
        participationRepository.save(new Participation(room3.getId(), ten.getId()));
        participationRepository.save(new Participation(room3.getId(), dar.getId()));

        //room4 에 참여한 참여자들
        participationRepository.save(new Participation(room4.getId(), joysun.getId()));
        participationRepository.save(new Participation(room4.getId(), pororo.getId()));
        participationRepository.save(new Participation(room4.getId(), movin.getId()));
        participationRepository.save(new Participation(room4.getId(), ten.getId()));
        participationRepository.save(new Participation(room4.getId(), dar.getId()));

        //room1 에서 매칭된 결과
        MatchResult result1 = new MatchResult(1L, pororo, ash, "https://github.com/example/java-racingcar/pull/2");
        result1.isRevieweeCompletedFeedback();
        result1.isReviewerCompletedFeedback();
        result1.reviewComplete();
        matchResultRepository.save(result1);
        MatchResult result2 = new MatchResult(1L, pororo, joysun, "https://github.com/example/java-racingcar/pull/3");
        result2.isRevieweeCompletedFeedback();
        result2.isReviewerCompletedFeedback();
        result2.reviewComplete();
        matchResultRepository.save(result2);

        MatchResult result3 = new MatchResult(1L, ash, joysun, "https://github.com/example/java-racingcar/pull/3");
        result3.isRevieweeCompletedFeedback();
        result3.isReviewerCompletedFeedback();
        result3.reviewComplete();
        matchResultRepository.save(result3);
        MatchResult result4 = new MatchResult(1L, ash, movin, "https://github.com/example/java-racingcar/pull/4");
        result4.isRevieweeCompletedFeedback();
        result4.isReviewerCompletedFeedback();
        result4.reviewComplete();
        matchResultRepository.save(result4);

        MatchResult result5 = new MatchResult(1L, joysun, movin, "https://github.com/example/java-racingcar/pull/4");
        result5.isRevieweeCompletedFeedback();
        result5.isReviewerCompletedFeedback();
        result5.reviewComplete();
        matchResultRepository.save(result5);
        MatchResult result6 = new MatchResult(1L, joysun, ten, "https://github.com/example/java-racingcar/pull/5");
        result6.isRevieweeCompletedFeedback();
        result6.isReviewerCompletedFeedback();
        result6.reviewComplete();
        matchResultRepository.save(result6);

        MatchResult result7 = new MatchResult(1L, movin, ten, "https://github.com/example/java-racingcar/pull/5");
        result7.isRevieweeCompletedFeedback();
        result7.isReviewerCompletedFeedback();
        result7.reviewComplete();
        matchResultRepository.save(result7);
        MatchResult result8 = new MatchResult(1L, movin, dar, "https://github.com/example/java-racingcar/pull/2");
        result8.isRevieweeCompletedFeedback();
        result8.isReviewerCompletedFeedback();
        result8.reviewComplete();
        matchResultRepository.save(result8);

        MatchResult result9 = new MatchResult(1L, ten, pororo, "https://github.com/example/java-racingcar/pull/1");
        result9.isRevieweeCompletedFeedback();
        result9.isReviewerCompletedFeedback();
        result9.reviewComplete();
        matchResultRepository.save(result9);
        MatchResult result10 = new MatchResult(1L, ten, dar, "https://github.com/example/java-racingcar/pull/2");
        result10.isRevieweeCompletedFeedback();
        result10.isReviewerCompletedFeedback();
        result10.reviewComplete();
        matchResultRepository.save(result10);

        MatchResult result11 = new MatchResult(1L, dar, pororo, "https://github.com/example/java-racingcar/pull/1");
        result11.isRevieweeCompletedFeedback();
        result11.isReviewerCompletedFeedback();
        result11.reviewComplete();
        matchResultRepository.save(result11);
        MatchResult result12 = new MatchResult(1L, dar, ash, "https://github.com/example/java-racingcar/pull/2");
        result12.isRevieweeCompletedFeedback();
        result12.isReviewerCompletedFeedback();
        result12.reviewComplete();
        matchResultRepository.save(result12);

        //room3 에서 매칭된 결과
        matchResultRepository.save(new MatchResult(3L, pororo, ash, "https://github.com/example/java-racingcar/pull/2"));
        matchResultRepository.save(new MatchResult(3L, pororo, joysun, "https://github.com/example/java-racingcar/pull/3"));

        matchResultRepository.save(new MatchResult(3L, ash, joysun, "https://github.com/example/java-racingcar/pull/3"));
        matchResultRepository.save(new MatchResult(3L, ash, movin, "https://github.com/example/java-racingcar/pull/4"));

        matchResultRepository.save(new MatchResult(3L, joysun, movin, "https://github.com/example/java-racingcar/pull/4"));
        matchResultRepository.save(new MatchResult(3L, joysun, ten, "https://github.com/example/java-racingcar/pull/5"));

        matchResultRepository.save(new MatchResult(3L, movin, ten, "https://github.com/example/java-racingcar/pull/5"));
        matchResultRepository.save(new MatchResult(3L, movin, dar, "https://github.com/example/java-racingcar/pull/2"));

        matchResultRepository.save(new MatchResult(3L, ten, pororo, "https://github.com/example/java-racingcar/pull/1"));
        matchResultRepository.save(new MatchResult(3L, ten, dar, "https://github.com/example/java-racingcar/pull/2"));

        matchResultRepository.save(new MatchResult(3L, dar, pororo, "https://github.com/example/java-racingcar/pull/1"));
        matchResultRepository.save(new MatchResult(3L, dar, ash, "https://github.com/example/java-racingcar/pull/2"));

        //room1 에서 작성된 피드백
        developFeedbackRepository.save(new DevelopFeedback(1L, ash, joysun, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));
        developFeedbackRepository.save(new DevelopFeedback(1L, ash, movin, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));

        socialFeedbackRepository.save(new SocialFeedback(1L, joysun, ash, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        socialFeedbackRepository.save(new SocialFeedback(1L, movin, ash, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));

        developFeedbackRepository.save(new DevelopFeedback(1L, pororo, ash, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));
        developFeedbackRepository.save(new DevelopFeedback(1L, pororo, joysun, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));

        socialFeedbackRepository.save(new SocialFeedback(1L, ash, pororo, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        socialFeedbackRepository.save(new SocialFeedback(1L, joysun, pororo, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));

        developFeedbackRepository.save(new DevelopFeedback(1L, joysun, movin, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));
        developFeedbackRepository.save(new DevelopFeedback(1L, joysun, ten, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));

        socialFeedbackRepository.save(new SocialFeedback(1L, movin, joysun, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        socialFeedbackRepository.save(new SocialFeedback(1L, ten, joysun, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));

        developFeedbackRepository.save(new DevelopFeedback(1L, movin, ten, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));
        developFeedbackRepository.save(new DevelopFeedback(1L, movin, dar, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));

        socialFeedbackRepository.save(new SocialFeedback(1L, ten, movin, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        socialFeedbackRepository.save(new SocialFeedback(1L, dar, movin, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));

        developFeedbackRepository.save(new DevelopFeedback(1L, ten, pororo, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));
        developFeedbackRepository.save(new DevelopFeedback(1L, ten, dar, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));

        socialFeedbackRepository.save(new SocialFeedback(1L, pororo, ten, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        socialFeedbackRepository.save(new SocialFeedback(1L, dar, ten, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));

        developFeedbackRepository.save(new DevelopFeedback(1L, ten, pororo, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));
        developFeedbackRepository.save(new DevelopFeedback(1L, ten, dar, 5, List.of(EASY_TO_UNDERSTAND_THE_CODE, MAKE_CODE_FOR_THE_PURPOSE), "너무 맘에 드네요~ 몇살이세요?", 5));

        socialFeedbackRepository.save(new SocialFeedback(1L, dar, pororo, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));
        socialFeedbackRepository.save(new SocialFeedback(1L, dar, ash, 5, List.of(KIND, GOOD_AT_EXPLAINING), "너무 맘에 드는 말투였어요~"));

        //프로필
        profileRepository.save(new corea.profile.domain.Profile(pororo, 1, 2, 3, 4.5f, 6.7f));
        profileRepository.save(new corea.profile.domain.Profile(ash, 2, 3, 4, 5.6f, 7.8f));
        profileRepository.save(new corea.profile.domain.Profile(joysun, 3, 4, 5, 6.7f, 8.9f));
        profileRepository.save(new corea.profile.domain.Profile(movin, 4, 5, 6, 7.8f, 9.10f));
        profileRepository.save(new corea.profile.domain.Profile(ten, 5, 6, 7, 8.9f, 10.11f));
        profileRepository.save(new corea.profile.domain.Profile(cho, 6, 7, 8, 9.10f, 11.12f));
        profileRepository.save(new corea.profile.domain.Profile(dar, 7, 8, 9, 10.11f, 12.13f));
    }
}
