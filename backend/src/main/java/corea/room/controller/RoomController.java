package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomParticipantResponses;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.service.RoomService;
import corea.scheduler.service.AutomaticMatchingService;
import corea.scheduler.service.AutomaticUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomControllerSpecification {

    private final RoomService roomService;
    private final AutomaticUpdateService automaticUpdateService;
    private final AutomaticMatchingService automaticMatchingService;

    @PostMapping
    public ResponseEntity<RoomResponse> create(@LoginMember AuthInfo authInfo, @RequestBody RoomCreateRequest request) {
        RoomResponse response = roomService.create(authInfo.getId(), request);

        automaticMatchingService.matchOnRecruitmentDeadline(response);
        automaticUpdateService.updateAtReviewDeadline(response);

        return ResponseEntity.created(URI.create(String.format("/rooms/%d", response.id())))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable long id, @AccessedMember AuthInfo authInfo) {
        RoomResponse response = roomService.findOne(id, authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<RoomParticipantResponses> participants(@PathVariable long id, @AccessedMember AuthInfo authInfo) {
        RoomParticipantResponses response = roomService.findParticipants(id, authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/participated")
    public ResponseEntity<RoomResponses> participatedRooms(@LoginMember AuthInfo authInfo) {
        RoomResponses response = roomService.findParticipatedRooms(authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        roomService.delete(id, authInfo.getId());

        automaticMatchingService.cancel(id);
        automaticUpdateService.cancel(id);

        return ResponseEntity.noContent()
                .build();
    }
}
