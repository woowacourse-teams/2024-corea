package corea;

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
                        "namejgc@naver.com", true, "119468757"));
        Member member2 = memberRepository.save(
                new Member("ashsty", null, "박민아",
                        null, false, "77227961"));
        Member member3 = memberRepository.save(
                new Member("youngsu5582", null, "이영수",
                        null, false, "98307410"));
        Member member4 = memberRepository.save(
                new Member("hjk0761", null, "김현중",
                        null, true, "80106238"));
        Member member5 = memberRepository.save(
                new Member("chlwlstlf", null, "최진실",
                        null, true, "63334368"));
        Member member6 = memberRepository.save(
                new Member("00kang", null, "강다빈",
                        null, true, "70834044"));
        Member member7 = memberRepository.save(
                new Member("pp449", null, "이상엽",
                        "mma7710@naver.com", true, "71641127"));
        Member member8 = memberRepository.save(
                new Member("pobi", null, "포비",
                        null, false, "99112400"));

        Room room1 = roomRepository.save(
                new Room("방 제목 1", "방 설명 1", 3,
                        null, null, List.of("TDD", "클린코드"),
                        1, 20, member1,
                        LocalDateTime.of(2024, 12, 20, 12, 0),
                        LocalDateTime.of(2024, 12, 26, 12, 0),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        Room room2 = roomRepository.save(
                new Room("방 제목 2", "방 설명 2", 3,
                        null, null, List.of("TDD"),
                        1, 20, member2,
                        LocalDateTime.of(2024, 12, 21, 12, 0),
                        LocalDateTime.of(2024, 12, 27, 12, 0),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        Room room3 = roomRepository.save(
                new Room("방 제목 3", "방 설명 3", 3,
                        null, null, List.of("TDD"),
                        1, 20, member3,
                        LocalDateTime.of(2024, 12, 22, 12, 0),
                        LocalDateTime.of(2024, 12, 28, 12, 0),
                        RoomClassification.ANDROID, RoomStatus.OPEN));
        Room room4 = roomRepository.save(
                new Room("방 제목 4", "방 설명 4", 3,
                        null, null, List.of("TDD"),
                        1, 20, member4,
                        LocalDateTime.of(2024, 12, 23, 12, 0),
                        LocalDateTime.of(2024, 12, 29, 12, 0),
                        RoomClassification.ANDROID, RoomStatus.OPEN));
        Room room5 = roomRepository.save(
                new Room("방 제목 5", "방 설명 5", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 24, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 0),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        Room room6 = roomRepository.save(
                new Room("방 제목 6", "방 설명 6", 3,
                        null, null, List.of("TDD"),
                        1, 20, member6,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 31, 12, 0),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        Room room7 = roomRepository.save(
                new Room("방 제목 7", "방 설명 7", 3,
                        null, null, List.of("TDD"),
                        1, 20, member7,
                        LocalDateTime.of(2024, 12, 25, 12, 10),
                        LocalDateTime.of(2024, 12, 31, 12, 2),
                        RoomClassification.FRONTEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 8", "방 설명 8", 3,
                        null, null, List.of("TDD", "클린코드"),
                        1, 20, member8,
                        LocalDateTime.of(2024, 12, 25, 12, 20),
                        LocalDateTime.of(2025, 1, 2, 12, 0),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        roomRepository.save(
                new Room("방 제목 9", "방 설명 9", 3,
                        null, null, List.of("TDD"),
                        1, 20, member8,
                        LocalDateTime.of(2024, 12, 25, 12, 30),
                        LocalDateTime.of(2025, 1, 3, 12, 0),
                        RoomClassification.BACKEND, RoomStatus.OPEN));
        Room closedRoom = roomRepository.save(
                new Room("방 제목 10", "방 설명 10", 3,
                        null, null, List.of("TDD", "클린코드"),
                        1, 20, member1,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 12),
                        RoomClassification.BACKEND, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 11", "방 설명 11", 3,
                        null, null, List.of("TDD"),
                        1, 20, member3,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 13),
                        RoomClassification.ANDROID, RoomStatus.CLOSE));
        roomRepository.save(
                new Room("방 제목 12", "방 설명 12", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 14),
                        RoomClassification.FRONTEND, RoomStatus.CLOSE));
        Room roomProgress = roomRepository.save(
                new Room("방 제목 13", "방 설명 13", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 15),
                        RoomClassification.BACKEND, RoomStatus.PROGRESS));
        roomRepository.save(
                new Room("방 제목 14", "방 설명 14", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 16),
                        RoomClassification.BACKEND, RoomStatus.PROGRESS));
        roomRepository.save(
                new Room("방 제목 15", "방 설명 15", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 17),
                        RoomClassification.BACKEND, RoomStatus.PROGRESS));
        roomRepository.save(
                new Room("방 제목 16", "방 설명 16", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 18),
                        RoomClassification.FRONTEND, RoomStatus.PROGRESS));
        roomRepository.save(
                new Room("방 제목 17", "방 설명 17", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 19),
                        RoomClassification.FRONTEND, RoomStatus.PROGRESS));
        roomRepository.save(
                new Room("방 제목 18", "방 설명 18", 3,
                        null, null, List.of("TDD"),
                        1, 20, member5,
                        LocalDateTime.of(2024, 12, 25, 12, 0),
                        LocalDateTime.of(2024, 12, 30, 12, 20),
                        RoomClassification.ANDROID, RoomStatus.PROGRESS));

        participationRepository.save(new Participation(room1, member2, MemberRole.BOTH, room1.getMatchingSize()));
        participationRepository.save(new Participation(room1, member3, MemberRole.BOTH, room1.getMatchingSize()));

        participationRepository.save(new Participation(room2, member3, MemberRole.BOTH, room2.getMatchingSize()));
        participationRepository.save(new Participation(room2, member4, MemberRole.BOTH, room2.getMatchingSize()));

        participationRepository.save(new Participation(room3, member4, MemberRole.BOTH, room3.getMatchingSize()));
        participationRepository.save(new Participation(room3, member5, MemberRole.BOTH, room3.getMatchingSize()));

        participationRepository.save(new Participation(room4, member5, MemberRole.BOTH, room4.getMatchingSize()));
        participationRepository.save(new Participation(room4, member6, MemberRole.BOTH, room4.getMatchingSize()));

        participationRepository.save(new Participation(room5, member6, MemberRole.BOTH, room5.getMatchingSize()));
        participationRepository.save(new Participation(room5, member7, MemberRole.BOTH, room5.getMatchingSize()));

        participationRepository.save(new Participation(room6, member1, MemberRole.BOTH, room6.getMatchingSize()));
        participationRepository.save(new Participation(room6, member7, MemberRole.BOTH, room6.getMatchingSize()));

        participationRepository.save(new Participation(room7, member1, MemberRole.BOTH, room7.getMatchingSize()));
        participationRepository.save(new Participation(room7, member2, MemberRole.BOTH, room7.getMatchingSize()));

        participationRepository.save(new Participation(closedRoom, member1, MemberRole.BOTH, closedRoom.getMatchingSize()));
        participationRepository.save(new Participation(roomProgress, member1, MemberRole.BOTH, roomProgress.getMatchingSize()));
    }
}
