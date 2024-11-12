package corea.room.service;

import corea.matchresult.domain.MatchResult;
import corea.matchresult.repository.MatchResultRepository;
import corea.member.domain.Member;
import corea.member.domain.MemberReader;
import corea.member.domain.MemberRole;
import corea.participation.domain.Participation;
import corea.participation.domain.ParticipationReader;
import corea.participation.domain.ParticipationStatus;
import corea.participation.domain.ParticipationWriter;
import corea.participation.repository.ParticipationRepository;
import corea.room.domain.*;
import corea.room.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private static final int RANDOM_DISPLAY_PARTICIPANTS_SIZE = 6;

    private final MatchResultRepository matchResultRepository;
    private final ParticipationRepository participationRepository;
    private final RoomAutomaticService roomAutomaticService;
    private final MemberReader memberReader;
    private final RoomWriter roomWriter;
    private final RoomReader roomReader;
    private final RoomMatchReader roomMatchReader;
    private final RoomMatchInfoWriter roomMatchInfoWriter;
    private final ParticipationWriter participationWriter;
    private final ParticipationReader participationReader;

    @Transactional
    public RoomResponse create(long memberId, RoomCreateRequest request) {
        Member manager = memberReader.findOne(memberId);
        Room room = roomWriter.create(manager, request);
        RoomMatchInfo roomMatchInfo = roomMatchInfoWriter.create(room, request.isPublic());

        Participation participation = participationWriter.create(room, manager, MemberRole.REVIEWER, ParticipationStatus.MANAGER);

        roomAutomaticService.createAutomatic(room);
        return RoomResponse.of(room, participation.getMemberRole(), ParticipationStatus.MANAGER, roomMatchInfo.isPublic());
    }

    @Transactional
    public RoomResponse update(long memberId, RoomUpdateRequest request) {
        Room room = roomReader.find(request.roomId());
        Member member = memberReader.findOne(memberId);

        Room updatedRoom = roomWriter.update(room, member, request);
        boolean isPublic = roomMatchReader.isPublicRoom(updatedRoom);
        MemberRole memberRole = participationReader.findMemberRole(room.getId(), memberId);

        roomAutomaticService.updateTime(updatedRoom);
        return RoomResponse.of(updatedRoom, memberRole, ParticipationStatus.MANAGER, isPublic);
    }

    @Transactional
    public void delete(long roomId, long memberId) {
        Room room = roomReader.find(roomId);
        Member member = memberReader.findOne(memberId);

        roomWriter.delete(room, member);
        roomAutomaticService.deleteAutomatic(room);
    }

    public RoomParticipantResponses findParticipants(long roomId, long memberId) {
        List<Participation> participants = findValidParticipants(roomId, memberId);
        Collections.shuffle(participants);

        List<RoomParticipantResponse> roomParticipantResponses = getRoomParticipantResponses(roomId, participants);
        return new RoomParticipantResponses(roomParticipantResponses, participants.size());
    }

    private List<Participation> findValidParticipants(long roomId, long memberId) {
        return participationRepository.findAllByRoomId(roomId)
                .stream()
                .filter(participation -> isValidParticipant(participation, memberId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isValidParticipant(Participation participation, long memberId) {
        return participation.isNotMatchingMemberId(memberId)
                && !participation.isReviewer()
                && !participation.isPullRequestNotSubmitted();
    }

    private List<RoomParticipantResponse> getRoomParticipantResponses(long roomId, List<Participation> participants) {
        return participants.stream()
                .map(participation -> getRoomParticipantResponse(roomId, participation))
                .filter(Objects::nonNull)
                .limit(RANDOM_DISPLAY_PARTICIPANTS_SIZE)
                .toList();
    }

    private RoomParticipantResponse getRoomParticipantResponse(long roomId, Participation participant) {
        List<MatchResult> matchResults = matchResultRepository.findAllByRevieweeIdAndRoomId(participant.getMembersId(), roomId);

        return matchResults.stream()
                .map(this::toRoomParticipantResponse)
                .findFirst()
                .orElse(null);
    }

    private RoomParticipantResponse toRoomParticipantResponse(MatchResult matchResult) {
        return RoomParticipantResponse.from(matchResult);
    }

    public RoomResponse getRoomById(long roomId) {
        return RoomResponse.from(roomReader.find(roomId));
    }
}
