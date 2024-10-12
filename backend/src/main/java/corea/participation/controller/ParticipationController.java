package corea.participation.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.participation.dto.ParticipationHttpRequest;
import corea.participation.dto.ParticipationRequest;
import corea.participation.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participate")
@RequiredArgsConstructor
public class ParticipationController implements ParticipationControllerSpecification {

    private final ParticipationService participationService;

    @Override
    @PostMapping("/{id}")
    public ResponseEntity<Void> participate(@PathVariable long id,
                                            @LoginMember AuthInfo authInfo,
                                            @RequestParam(value = "role", defaultValue = "both") String role,
                                            @RequestBody ParticipationHttpRequest participationHttpRequest) {
        participationService.participate(new ParticipationRequest(id, authInfo.getId(), role, participationHttpRequest.matchingSize()));
        return ResponseEntity.ok()
                .build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelParticipate(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        participationService.cancel(id, authInfo.getId());
        return ResponseEntity.ok()
                .build();
    }
}
