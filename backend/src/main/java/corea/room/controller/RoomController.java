package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.matching.dto.ReviewInfo;
import corea.matching.service.MatchResultService;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomControllerSpecification {

    private final RoomService roomService;
    private final MatchResultService matchResultService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable long id, @AccessedMember AuthInfo authInfo) {
        RoomResponse response = roomService.findOne(id, authInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reviewers")
    public ResponseEntity<List<ReviewInfo>> reviewers(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        List<ReviewInfo> response = matchResultService.findReviewers(authInfo.getId(), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reviewees")
    public ResponseEntity<List<ReviewInfo>> reviewees(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        List<ReviewInfo> response = matchResultService.findReviewees(authInfo.getId(), id);
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
    public ResponseEntity<RoomResponses> openedRooms(@AccessedMember AuthInfo authInfo,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression,
                                                     @RequestParam(defaultValue = "0") int page) {
        if (authInfo.isAnonymous()) {
            RoomResponses response = roomService.findOpenedRoomsWithoutMember(expression, page);
            return ResponseEntity.ok(response);
        }
        RoomResponses response = roomService.findOpenedRoomsWithMember(authInfo.getId(), expression, page);
        return ResponseEntity.ok(response);
    }
}
