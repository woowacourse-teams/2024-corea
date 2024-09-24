package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.matching.dto.MatchResultResponses;
import corea.matching.service.MatchResultService;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.service.RoomService;
import corea.scheduler.service.AutomaticMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomControllerSpecification {

    private final RoomService roomService;
    private final MatchResultService matchResultService;
    private final AutomaticMatchingService automaticMatchingService;

    @PostMapping
    public ResponseEntity<RoomResponse> create(@LoginMember AuthInfo authInfo, @RequestBody RoomCreateRequest request) {
        RoomResponse response = roomService.create(authInfo.getId(), request);
        automaticMatchingService.matchOnRecruitmentDeadline(response);

        return ResponseEntity.created(URI.create(String.format("/rooms/%d", response.id())))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable long id, @AccessedMember AuthInfo authInfo) {
        RoomResponse response = roomService.findOne(id, authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reviewers")
    public ResponseEntity<MatchResultResponses> reviewers(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        MatchResultResponses response = matchResultService.findReviewers(authInfo.getId(), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reviewees")
    public ResponseEntity<MatchResultResponses> reviewees(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        MatchResultResponses response = matchResultService.findReviewees(authInfo.getId(), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/participated")
    public ResponseEntity<RoomResponses> participatedRooms(@LoginMember AuthInfo authInfo) {
        RoomResponses response = roomService.findParticipatedRooms(authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/opened")
    public ResponseEntity<RoomResponses> openedRooms(@AccessedMember AuthInfo authInfo,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression) {
        RoomResponses response = roomService.findRoomsWithRoomStatus(authInfo.getId(), page, expression, RoomStatus.OPEN);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/progress")
    public ResponseEntity<RoomResponses> progressRooms(@AccessedMember AuthInfo authInfo,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(value = "classification", defaultValue = "all") String expression) {
        RoomResponses response = roomService.findRoomsWithRoomStatus(authInfo.getId(), page, expression, RoomStatus.PROGRESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/closed")
    public ResponseEntity<RoomResponses> closedRooms(@AccessedMember AuthInfo authInfo,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression) {
        RoomResponses response = roomService.findRoomsWithRoomStatus(authInfo.getId(), page, expression, RoomStatus.CLOSE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        roomService.delete(id, authInfo.getId());
        automaticMatchingService.cancel(id);

        return ResponseEntity.noContent()
                .build();
    }
}
