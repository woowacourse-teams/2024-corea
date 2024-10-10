package corea.bdd;

import corea.matching.dto.MatchResultResponse;
import corea.matching.dto.MatchResultResponses;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.List;

public class MatchingGiven {
    public static void 매칭(String accessToken, long roomId) {
        //@formatter:off
        RestAssured.given().auth().oauth2(accessToken).contentType(ContentType.JSON)
                .when().post("/rooms/"+roomId+"/matching")
                .then().assertThat().statusCode(200);
        //@formatter:on
    }

    public static List<MatchResultResponse> 리뷰이_목록_조회(String accessToken, long roomId) {
        //@formatter:off
        MatchResultResponses responses =  RestAssured.given().auth().oauth2(accessToken).contentType(ContentType.JSON)
                .when().get("/rooms/"+roomId+"/reviewees")
                .then().assertThat().statusCode(200).extract().as(MatchResultResponses.class);
        //@formatter:on
        return responses.matchResultResponses();
    }
}
