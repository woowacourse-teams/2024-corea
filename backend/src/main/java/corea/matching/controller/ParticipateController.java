package corea.matching.controller;

import corea.auth.annotation.LoginUser;
import corea.auth.domain.AuthInfo;
import corea.matching.dto.ParticipationRequest;
import corea.matching.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/participate")
@RequiredArgsConstructor
public class ParticipateController implements ParticipationControllerSpecification {
    private final ParticipationService participationService;

    @PostMapping("/{id}")
    public ResponseEntity<Void> participate(@PathVariable long id, @LoginUser AuthInfo authInfo) {
        participationService.participate(new ParticipationRequest(id,authInfo.getId()));
        return ResponseEntity.ok()
                .build();
    }
}
