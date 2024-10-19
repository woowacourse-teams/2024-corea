package corea.room.controller;

import corea.auth.annotation.AccessedMember;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import corea.room.service.RoomDetailsInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomDetailsInquiryController implements RoomDetailsInquiryControllerSpecification {

    private final RoomDetailsInquiryService roomDetailsInquiryService;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable long id, @AccessedMember AuthInfo authInfo) {
        RoomResponse response = roomDetailsInquiryService.findOne(id, authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/participated")
    public ResponseEntity<RoomResponses> participatedRooms(@LoginMember AuthInfo authInfo,
                                                           @RequestParam(defaultValue = "false") boolean includeClosed) {
        RoomResponses response = roomDetailsInquiryService.findParticipatedRooms(authInfo.getId(), includeClosed);
        return ResponseEntity.ok(response);
    }
}
