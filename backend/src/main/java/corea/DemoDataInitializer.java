package corea;

import corea.matching.domain.MatchResult;
import corea.matching.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.participation.domain.Participation;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.RoomClassification;
import corea.room.domain.Room;
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

@Profile("!test")
@Component
@Transactional
@RequiredArgsConstructor
public class DemoDataInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;
    private final MatchResultRepository matchResultRepository;

    @Override
    public void run(ApplicationArguments args) {
        Member member1 = memberRepository.save(
                new Member("jcoding-play", "https://avatars.githubusercontent.com/u/119468757?v=4", "조경찬",
                        "pororo@email.com", true));
        Member member2 = memberRepository.save(
                new Member("ashsty", "https://avatars.githubusercontent.com/u/77227961?v=4", "박민아",
                        "ash@email.com", false));
        Member member3 = memberRepository.save(
                new Member("youngsu5582", "https://avatars.githubusercontent.com/u/98307410?v=4", "이영수",
                        "joysun@email.com", false));
        Member member4 = memberRepository.save(
                new Member("hjk0761", "https://avatars.githubusercontent.com/u/80106238?s=96&v=4", "김현중",
                        "movin@email.com", true));
        Member member5 = memberRepository.save(
                new Member("chlwlstlf", "https://avatars.githubusercontent.com/u/63334368?v=4", "최진실",
                        "tenten@email.com", true));
        Member member6 = memberRepository.save(
                new Member("00kang", "https://avatars.githubusercontent.com/u/70834044?v=4", "강다빈",
                        "choco@email.com", true));
        Member member7 = memberRepository.save(
                new Member("pp449", "https://avatars.githubusercontent.com/u/71641127?v=4", "이상엽",
                        "darr@email.com", true));

        // 이미 모집 완료되어 매칭까지 진행된 방
        Room room1 = roomRepository.save(
                new Room("자바 레이싱 카 - MVC", "MVC 패턴을 아시나요?", 2,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("MVC", "자바", "디자인패턴"), 5, 30, member1,
                        LocalDateTime.now().minusDays(7),
                        LocalDateTime.now().plusDays(7),
                        RoomClassification.BACKEND, RoomStatus.CLOSED));

        // member1 기준 아직 참여하지 않았고, 참여 가능한 방
        Room room2 = roomRepository.save(
                new Room("자바 레이싱 카 - TDD", "애쉬와 함께하는 TDD", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD", "클린코드", "자바"), 6, 30, member2,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.BACKEND, RoomStatus.OPENED));
        Room room3 = roomRepository.save(
                new Room("자바 로또 - 객체지향", "객체지향 한 접시", 5,
                        "https://github.com/example/java-lotto",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("객체지향", "자바"), 1, 20, member3,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(14),
                        RoomClassification.BACKEND, RoomStatus.OPENED));
        Room room4 = roomRepository.save(
                new Room("코틀린 숫자 야구", "코틀린 기초 같이 할 사람", 3,
                        "https://github.com/example/kotlin-baseball",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("코틀린"), 1, 20, member4,
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now().plusDays(14),
                        RoomClassification.ANDROID, RoomStatus.OPENED));
        Room room5 = roomRepository.save(
                new Room("자바스크립트 크리스마스", "진짜 요구사항대로 구현하기!", 3,
                        "https://github.com/example/javascript-christmas",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("구현", "자바스크립트"), 1, 20, member5,
                        LocalDateTime.now().plusDays(5),
                        LocalDateTime.now().plusDays(19),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        Room room6 = roomRepository.save(
                new Room("방 제목 6", "방 설명 6", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member6,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        Room room7 = roomRepository.save(
                new Room("방 제목 7", "방 설명 7", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member7,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 8", "방 설명 8", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD", "클린코드"),
                        1, 20, member1,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.BACKEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 9", "방 설명 9", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member3,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.ANDROID, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 10", "방 설명 10", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.OPENED));
        roomRepository.save(
                new Room("방 제목 12", "방 설명 12", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 13", "방 설명 13", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 14", "방 설명 14", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 15", "방 설명 15", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 16", "방 설명 16", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 17", "방 설명 17", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 18", "방 설명 18", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 19", "방 설명 19", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 20", "방 설명 20", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));
        roomRepository.save(
                new Room("방 제목 21", "방 설명 21", 3,
                        "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.now().plusDays(3),
                        LocalDateTime.now().plusDays(17),
                        RoomClassification.FRONTEND, RoomStatus.CLOSED));

        //room1 에 참여한 참여자들
        participationRepository.save(new Participation(room1.getId(), member1.getId()));
        participationRepository.save(new Participation(room1.getId(), member2.getId()));
        participationRepository.save(new Participation(room1.getId(), member3.getId()));
        participationRepository.save(new Participation(room1.getId(), member4.getId()));
        participationRepository.save(new Participation(room1.getId(), member5.getId()));

        //room2 에 참여한 참여자들
        participationRepository.save(new Participation(room2.getId(), member6.getId()));
        participationRepository.save(new Participation(room2.getId(), member2.getId()));
        participationRepository.save(new Participation(room2.getId(), member3.getId()));
        participationRepository.save(new Participation(room2.getId(), member4.getId()));
        participationRepository.save(new Participation(room2.getId(), member5.getId()));

        participationRepository.save(new Participation(room3.getId(), member3.getId()));

        participationRepository.save(new Participation(room4.getId(), member4.getId()));

        participationRepository.save(new Participation(room5.getId(), member5.getId()));

        participationRepository.save(new Participation(room6.getId(), member6.getId()));

        participationRepository.save(new Participation(room7.getId(), member7.getId()));

        //room1 에서 매칭된 결과
        matchResultRepository.save(new MatchResult(1L, member1, member2, "https://github.com/example/java-racingcar/pull/2"));
        matchResultRepository.save(new MatchResult(1L, member1, member3, "https://github.com/example/java-racingcar/pull/3"));
        matchResultRepository.save(new MatchResult(1L, member1, member4, "https://github.com/example/java-racingcar/pull/4"));

        matchResultRepository.save(new MatchResult(1L, member2, member3, "https://github.com/example/java-racingcar/pull/3"));
        matchResultRepository.save(new MatchResult(1L, member2, member4, "https://github.com/example/java-racingcar/pull/4"));
        matchResultRepository.save(new MatchResult(1L, member2, member5, "https://github.com/example/java-racingcar/pull/5"));

        matchResultRepository.save(new MatchResult(1L, member3, member4, "https://github.com/example/java-racingcar/pull/4"));
        matchResultRepository.save(new MatchResult(1L, member3, member5, "https://github.com/example/java-racingcar/pull/5"));
        matchResultRepository.save(new MatchResult(1L, member3, member1, "https://github.com/example/java-racingcar/pull/1"));

        matchResultRepository.save(new MatchResult(1L, member4, member5, "https://github.com/example/java-racingcar/pull/5"));
        matchResultRepository.save(new MatchResult(1L, member4, member1, "https://github.com/example/java-racingcar/pull/1"));
        matchResultRepository.save(new MatchResult(1L, member4, member2, "https://github.com/example/java-racingcar/pull/2"));

        matchResultRepository.save(new MatchResult(1L, member5, member1, "https://github.com/example/java-racingcar/pull/1"));
        matchResultRepository.save(new MatchResult(1L, member5, member2, "https://github.com/example/java-racingcar/pull/2"));
        matchResultRepository.save(new MatchResult(1L, member5, member3, "https://github.com/example/java-racingcar/pull/3"));
    }
}
