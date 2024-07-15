package corea.service;

import corea.domain.Member;
import corea.domain.Room;
import corea.dto.RoomCreateRequest;
import corea.dto.RoomResponse;
import corea.repository.MemberRepository;
import corea.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public RoomService(final RoomRepository roomRepository, final MemberRepository memberRepository) {
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    public RoomResponse create(RoomCreateRequest request) {
        final Room room = roomRepository.save(request.toEntity());
        final Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d에 해당하는 멤버가 없습니다.", request.memberId())));
        return RoomResponse.from(room, member.getEmail());
    }
}
