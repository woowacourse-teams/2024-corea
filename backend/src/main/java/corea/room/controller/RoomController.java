package corea.room.controller;

import corea.member.domain.Member;
import corea.room.config.LoginMember;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable long id) {
        RoomResponse response = roomService.findOne(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms")
    public ResponseEntity<RoomResponses> rooms() {
        RoomResponses response = roomService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms/participated")
    public ResponseEntity<RoomResponses> participatedRooms(@LoginMember Member member) {
        RoomResponses response = roomService.findParticipatedRooms(member);
        return ResponseEntity.ok(response);
    }
}
