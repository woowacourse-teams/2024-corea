package corea.room;

import corea.dto.RoomResponse;
import corea.dto.RoomResponses;
import corea.service.RoomService;
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
    public ResponseEntity<RoomResponse> room(@PathVariable final long id) {
        final RoomResponse response = roomService.findOne(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms")
    public ResponseEntity<RoomResponses> rooms() {
        final RoomResponses response = roomService.findAll();
        return ResponseEntity.ok(response);
    }
}
