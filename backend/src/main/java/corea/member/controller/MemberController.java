package corea.member.controller;

import corea.dto.ApiResponse;
import corea.dto.MatchedGroupResponse;
import corea.member.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MatchService matchService;

    @GetMapping("/my/reviewer")
    public MatchedGroupResponse getMyReviewer(@RequestHeader("Authorization") final String email) {
        return matchService.getMatchedGroup(email);
    }

    @GetMapping("/my/reviewee")
    public MatchedGroupResponse getMyReviewee(@RequestHeader("Authorization") final String email) {
        return matchService.getMatchedGroup(email);
    }

    @PostMapping("/match/{roomId}")
    public ApiResponse match(@PathVariable final long roomId) {
        matchService.match(roomId);
        return new ApiResponse("GOOD");
    }
}
