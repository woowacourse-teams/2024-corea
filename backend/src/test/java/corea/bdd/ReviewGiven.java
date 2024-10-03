package corea.bdd;

import corea.review.dto.ReviewRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReviewGiven {
    public static void 리뷰_완료(String accessToken, long roomId, long revieweeId) {
        //@formatter:off
        RestAssured.given().auth().oauth2(accessToken).body(new ReviewRequest(roomId,revieweeId)).contentType(ContentType.JSON)
                .when().post("/review/complete")
                .then().assertThat().statusCode(200);
        //@formatter:on
    }
}
