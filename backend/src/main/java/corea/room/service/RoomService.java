package corea.room.service;

import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationReader;
import corea.participation.domain.ParticipationStatus;
import corea.participation.domain.ParticipationWriter;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.Room;
import corea.room.domain.RoomReader;
import corea.room.domain.RoomWriter;
import corea.room.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int RANDOM_DISPLAY_PARTICIPANTS_SIZE = 6;

    private final MatchResultRepository matchResultRepository;
    private final ParticipationRepository participationRepository;
    private final RoomAutomaticService roomAutomaticService;
    private final ParticipationWriter participationWriter;
    private final MemberReader memberReader;
    private final RoomWriter roomWriter;
    private final ParticipationReader participationReader;
    private final RoomReader roomReader;

    @Transactional
    public RoomResponse create(long memberId, RoomCreateRequest request) {
        Member manager = memberReader.findOne(memberId);
        Room room = roomWriter.create(manager, request);

        Participation participation = participationWriter.create(room, manager, MemberRole.REVIEWER, ParticipationStatus.MANAGER);

        roomAutomaticService.createAutomatic(room);
        return RoomResponse.of(room, participation.getMemberRole(), ParticipationStatus.MANAGER);
    }

    @Transactional
    public RoomResponse update(long memberId, RoomUpdateRequest request) {
        Room room = roomReader.find(request.roomId());
        Member member = memberReader.findOne(memberId);

        Room updatedRoom = roomWriter.update(room, member, request);
        MemberRole memberRole = participationReader.findMemberRole(room.getId(), memberId);

        roomAutomaticService.updateTime(updatedRoom);
        return RoomResponse.of(updatedRoom, memberRole, ParticipationStatus.MANAGER);
    }

    @Transactional
    public void delete(long roomId, long memberId) {
        Room room = roomReader.find(roomId);
        Member member = memberReader.findOne(memberId);

        roomWriter.delete(room, member);
        roomAutomaticService.deleteAutomatic(room);
    }

    public RoomParticipantResponses findParticipants(long roomId, long memberId) {
        List<Participation> participants = new ArrayList<>(participationRepository.findAllByRoomId(roomId)
                .stream()
                .filter(participation -> isValidParticipant(participation, memberId))
                .toList());

        Collections.shuffle(participants);

        List<RoomParticipantResponse> roomParticipantResponses = participants.stream()
                .limit(RANDOM_DISPLAY_PARTICIPANTS_SIZE)
                .map(participation -> getRoomParticipantResponse(roomId, participation))
                .filter(Objects::nonNull)
                .toList();
        return new RoomParticipantResponses(roomParticipantResponses, participants.size());
    }

    private boolean isValidParticipant(Participation participation, long memberId) {
        return participation.isNotMatchingMemberId(memberId)
                && !participation.isReviewer()
                && !participation.isPullRequestNotSubmitted();
    }

    private RoomParticipantResponse getRoomParticipantResponse(long roomId, Participation participant) {
        return matchResultRepository.findAllByRevieweeIdAndRoomId(participant.getMembersId(), roomId)
                .stream()
                .findFirst()
                .map(matchResult -> new RoomParticipantResponse(
                        matchResult.getReviewee()
                                .getGithubUserId(), matchResult.getReviewee()
                        .getUsername(), matchResult.getPrLink(), matchResult.getReviewee()
                        .getThumbnailUrl()))
                .orElse(null);
    }

    public RoomResponse getRoomById(long roomId) {
        return RoomResponse.from(roomReader.find(roomId));
    }
}
