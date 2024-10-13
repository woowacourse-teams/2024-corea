package corea.matchresult.controller;

import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import corea.matchresult.dto.MatchResultResponses;
import corea.matchresult.service.MatchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class MatchingResultController implements MatchingResultControllerSpecification {

    private final MatchResultService matchResultService;

    @GetMapping("/{id}/reviewers")
    public ResponseEntity<MatchResultResponses> reviewers(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        MatchResultResponses response = matchResultService.findReviewers(authInfo.getId(), id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reviewees")
    public ResponseEntity<MatchResultResponses> reviewees(@PathVariable long id, @LoginMember AuthInfo authInfo) {
        MatchResultResponses response = matchResultService.findReviewees(authInfo.getId(), id);
        return ResponseEntity.ok(response);
    }
}
