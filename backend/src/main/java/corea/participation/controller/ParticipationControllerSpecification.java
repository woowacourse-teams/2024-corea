package corea.participation.controller;

import corea.auth.domain.AuthInfo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface ParticipationControllerSpecification {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "해당하는 방이 없는 경우", value = """
                                    {
                                        "message": "1에 해당하는 방 없습니다."
                                    }
                                    """)
                    })),
            }
    )
    ResponseEntity<Void> participate(long id, AuthInfo authInfo);
}
