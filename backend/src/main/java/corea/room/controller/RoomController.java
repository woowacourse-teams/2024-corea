package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable long id, @AccessedMember AuthInfo authInfo) {
        RoomResponse response = roomService.findOne(id, authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/participated")
    public ResponseEntity<RoomResponses> participatedRooms(@LoginMember AuthInfo authInfo) {
        RoomResponses response = roomService.findParticipatedRooms(authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/opened")
    public ResponseEntity<RoomResponses> openedRooms(@AccessedMember AuthInfo authInfo,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression,
                                                     @RequestParam(defaultValue = "0") int page) {
        RoomResponses response = roomService.findOpenedRooms(authInfo.getId(), expression, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/closed")
    public ResponseEntity<RoomResponses> closedRooms(@RequestParam(value = "classification", defaultValue = "all") String expression,
                                                     @RequestParam(defaultValue = "0") int page) {
        RoomResponses response = roomService.findClosedRooms(expression, page);
        return ResponseEntity.ok(response);
    }
}
