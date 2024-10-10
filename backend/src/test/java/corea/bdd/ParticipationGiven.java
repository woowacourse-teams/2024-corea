package corea.bdd;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ParticipationGiven {

    public static void 방_참가(String accessToken, long roomId) {
        //@formatter:off
        RestAssured.given().auth().oauth2(accessToken).contentType(ContentType.JSON)
                .when().post("/participate/"+roomId)
                .then().assertThat().statusCode(200);
        //@formatter:on
    }
}
