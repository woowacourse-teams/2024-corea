package corea;

import corea.matching.domain.Participation;
import corea.matching.repository.ParticipationRepository;
import corea.member.domain.Member;
import corea.member.repository.MemberRepository;
import corea.room.domain.Classification;
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

@Profile("test")
@Component
@Transactional
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;

    @Override
    public void run(ApplicationArguments args) {
        Member member1 = memberRepository.save(
                new Member("jcoding-play", null, "조경찬",
                        "namejgc@naver.com", true, 5f));
        Member member2 = memberRepository.save(
                new Member("ashsty", null, "박민아",
                        null, false, 1.5f));
        Member member3 = memberRepository.save(
                new Member("youngsu5582", null, "이영수",
                        null, false, 4f));
        Member member4 = memberRepository.save(
                new Member("hjk0761", null, "김현중",
                        null, true, 3f));
        Member member5 = memberRepository.save(
                new Member("chlwlstlf", null, "최진실",
                        null, true, 2f));
        Member member6 = memberRepository.save(
                new Member("00kang", null, "강다빈",
                        null, true, 1f));
        Member member7 = memberRepository.save(
                new Member("pp449", null, "이상엽",
                        "mma7710@naver.com", true, 4.8f));

        Room room1 = roomRepository.save(
                new Room("방 제목 1", "방 설명 1", 3,
                        null, null, List.of("TDD","클린코드"),
                        1, 20, member1,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.BACKEND, RoomStatus.OPENED));
        Room room2 = roomRepository.save(
                new Room("방 제목 2", "방 설명 2", 3,
                        null, null, List.of("TDD"),
                        1, 20, member2,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.BACKEND, RoomStatus.OPENED));
        Room room3 = roomRepository.save(
                new Room("방 제목 3", "방 설명 3", 3,
                        null, null, List.of("TDD"),
                        1, 20, member3,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.ANDROID, RoomStatus.OPENED));
        Room room4 = roomRepository.save(
                new Room("방 제목 4", "방 설명 4", 3,
                        null, null, List.of("TDD"),
                        1, 20, member4,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.ANDROID, RoomStatus.OPENED));
        Room room5 = roomRepository.save(
                new Room("방 제목 5", "방 설명 5", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.FRONTEND, RoomStatus.OPENED));
        Room room6 = roomRepository.save(
                new Room("방 제목 6", "방 설명 6", 3,
                        null, null, List.of("TDD"),
                        1, 20, member6,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.FRONTEND, RoomStatus.OPENED));
        Room room7 = roomRepository.save(
                new Room("방 제목 7", "방 설명 7", 3,
                        null, null, List.of("TDD"),
                        1, 20, member7,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        Classification.FRONTEND, RoomStatus.OPENED));

        participationRepository.save(new Participation(room1.getId(), member2.getId()));
        participationRepository.save(new Participation(room1.getId(), member3.getId()));

        participationRepository.save(new Participation(room2.getId(), member3.getId()));
        participationRepository.save(new Participation(room2.getId(), member4.getId()));

        participationRepository.save(new Participation(room3.getId(), member4.getId()));
        participationRepository.save(new Participation(room3.getId(), member5.getId()));

        participationRepository.save(new Participation(room4.getId(), member5.getId()));
        participationRepository.save(new Participation(room4.getId(), member6.getId()));

        participationRepository.save(new Participation(room5.getId(), member6.getId()));
        participationRepository.save(new Participation(room5.getId(), member7.getId()));

        participationRepository.save(new Participation(room6.getId(), member1.getId()));
        participationRepository.save(new Participation(room6.getId(), member7.getId()));

        participationRepository.save(new Participation(room7.getId(), member1.getId()));
        participationRepository.save(new Participation(room7.getId(), member2.getId()));
    }
}
