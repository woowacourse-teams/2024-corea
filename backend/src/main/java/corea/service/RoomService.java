package corea.service;

import corea.domain.Member;
import corea.domain.Room;
import corea.dto.RoomCreateRequest;
import corea.dto.RoomResponse;
import corea.dto.RoomResponses;
import corea.repository.MemberRepository;
import corea.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public RoomService(final RoomRepository roomRepository, final MemberRepository memberRepository) {
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    public RoomResponse create(final RoomCreateRequest request) {
        final Room room = roomRepository.save(request.toEntity());
        final long memberId = request.memberId();
        final Member member = getMember(memberId);
        return RoomResponse.from(room, member.getEmail());
    }

    public RoomResponse findOne(final long id) {
        final Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d에 해당하는 방이 없습니다.", id)));
        final Member member = getMember(room.getMemberId());

        return RoomResponse.from(room, member.getEmail());
    }

    private Member getMember(final long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d에 해당하는 멤버가 없습니다.", memberId)));
    }

    public RoomResponses findAll() {
        final List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> {
                    final long memberId = room.getMemberId();
                    final Member member = getMember(memberId);
                    return RoomResponse.from(room, member.getEmail());
                })
                .collect(collectingAndThen(toList(), RoomResponses::new));
    }
}
