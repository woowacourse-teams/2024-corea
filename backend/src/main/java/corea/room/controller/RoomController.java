package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.room.dto.*;
import corea.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomControllerSpecification {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> create(@LoginMember AuthInfo authInfo, @RequestBody RoomCreateRequest request) {
        RoomResponse response = roomService.create(authInfo.getId(), request);

        return ResponseEntity.created(URI.create(String.format("/rooms/%d", response.id())))
                .body(response);
    }

    @PutMapping
    public ResponseEntity<RoomResponse> update(@LoginMember AuthInfo authInfo, @RequestBody RoomUpdateRequest request) {
        RoomResponse response = roomService.update(authInfo.getId(), request);

        return ResponseEntity.ok()
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        roomService.delete(id, authInfo.getId());

        return ResponseEntity.noContent()
                .build();
    }
}
