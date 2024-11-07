package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.domain.AuthInfo;
import corea.room.domain.RoomClassification;
import corea.room.domain.RoomStatus;
import corea.room.dto.RoomResponses;
import corea.room.dto.RoomSearchResponses;
import corea.room.service.RoomInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomInquiryController implements RoomInquiryControllerSpecification {

    private final RoomInquiryService roomInquiryService;

    @GetMapping("/opened")
    public ResponseEntity<RoomResponses> openedRooms(@AccessedMember AuthInfo authInfo,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression) {
        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(authInfo.getId(), page, expression, RoomStatus.OPEN);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/progress")
    public ResponseEntity<RoomResponses> progressRooms(@AccessedMember AuthInfo authInfo,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(value = "classification", defaultValue = "all") String expression) {
        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(authInfo.getId(), page, expression, RoomStatus.PROGRESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/closed")
    public ResponseEntity<RoomResponses> closedRooms(@AccessedMember AuthInfo authInfo,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(value = "classification", defaultValue = "all") String expression) {
        RoomResponses response = roomInquiryService.findRoomsWithRoomStatus(authInfo.getId(), page, expression, RoomStatus.CLOSE);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<RoomSearchResponses> search(@AccessedMember AuthInfo authInfo,
                                                      @RequestParam(value = "roomStatus") RoomStatus status,
                                                      @RequestParam(value = "roomClassification", defaultValue = "ALL") RoomClassification classification,
                                                      @RequestParam(value = "searchKeyword") String keywordTitle) {
        RoomSearchResponses response = roomInquiryService.search(authInfo.getId(), status, classification, keywordTitle);

        return ResponseEntity.ok()
                .body(response);
    }
}
