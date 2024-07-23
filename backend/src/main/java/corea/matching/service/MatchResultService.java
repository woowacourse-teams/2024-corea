package corea.matching.service;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.matching.domain.MatchResult;
import corea.matching.dto.MatchResultResponse;
import corea.matching.dto.MatchResultResponses;
import corea.matching.repository.MatchResultRepository;
import corea.member.repository.MemberRepository;
import corea.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MatchResultService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final MatchResultRepository matchResultRepository;

    public MatchResultResponses findReviewers(long memberId, long roomId) {
        validateExistence(memberId, roomId);
        List<MatchResult> results = matchResultRepository.findAllByToMemberIdAndRoomId(memberId, roomId);

        return new MatchResultResponses(results.stream()
                .map(result -> MatchResultResponse.of(result, result.getFromMember()))
                .toList());
    }

    public MatchResultResponses findReviewees(long memberId, long roomId) {
        validateExistence(memberId, roomId);
        List<MatchResult> results = matchResultRepository.findAllByFromMemberIdAndRoomId(memberId, roomId);

        return new MatchResultResponses(results.stream()
                .map(result -> MatchResultResponse.of(result, result.getToMember()))
                .toList());
    }

    private void validateExistence(long memberId, long roomId) {
        if (!memberRepository.existsById(memberId)) {
            throw new CoreaException(ExceptionType.MEMBER_NOT_FOUND, String.format("%d에 해당하는 멤버가 없습니다.", memberId));
        }
        if (!roomRepository.existsById(roomId)) {
            throw new CoreaException(ExceptionType.ROOM_NOT_FOUND, String.format("%d에 해당하는 방이 없습니다.", roomId));
        }
    }
}
