package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.member.domain.Member;
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
    public ResponseEntity<RoomResponse> room(@PathVariable long id) {
        RoomResponse response = roomService.findOne(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<RoomResponses> rooms() {
        RoomResponses response = roomService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/participated")
    public ResponseEntity<RoomResponses> participatedRooms(@LoginMember AuthInfo authInfo) {
        RoomResponses response = roomService.findParticipatedRooms(authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/opened")
    public ResponseEntity<RoomResponses> openedRooms(@AccessedMember Member member,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression,
                                                     @RequestParam(defaultValue = "0") int page) {
        if (member == null) {
            RoomResponses response = roomService.findOpenedRoomsWithoutMember(expression, page);
            return ResponseEntity.ok(response);
        }
        RoomResponses response = roomService.findOpenedRoomsWithMember(member.getId(), expression, page);
        return ResponseEntity.ok(response);
    }
}
