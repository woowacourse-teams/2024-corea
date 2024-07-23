package corea.room.controller;

import corea.auth.domain.AuthInfo;
import corea.matching.dto.MatchResultResponses;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface RoomControllerSpecification {

    ResponseEntity<RoomResponse> room(long id, AuthInfo authInfo);

    ResponseEntity<RoomResponses> participatedRooms(AuthInfo authInfo);

    ResponseEntity<RoomResponses> openedRooms(AuthInfo authInfo, String expression, int page);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "리뷰어를 조회할 수 없는 경우", value = """
                                    {
                                        "message": "리뷰어 정보를 찾을 수 없습니다."
                                    }
                                    """)
                    })),
            }
    )
    ResponseEntity<MatchResultResponses> reviewers(long id, AuthInfo authInfo);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "리뷰이를 조회할 수 없는 경우", value = """
                                    {
                                        "message": "리뷰이 정보를 찾을 수 없습니다."
                                    }
                                    """)
                    })),
            }
    )
    ResponseEntity<MatchResultResponses> reviewees(long id, AuthInfo authInfo);
}
