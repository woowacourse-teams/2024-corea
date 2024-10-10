package corea.bdd;

import corea.room.domain.RoomClassification;
import corea.room.dto.RoomCreateRequest;
import corea.room.dto.RoomResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.util.List;

public class RoomGiven {
    public static RoomResponse 방생성(String accessToken, int matchingSize) {

        LocalDateTime now = LocalDateTime.now();
        final RoomCreateRequest request =
                new RoomCreateRequest(
                        "제목", "내용", "https://github.com/example/java-racingcar",
                        "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004",
                        matchingSize,
                        List.of("TDD", "클린코드"),
                        200,
                        now.plusHours(2),
                        now.plusDays(2),
                        RoomClassification.BACKEND
                );

        //@formatter:off
        return RestAssured.given().auth().oauth2(accessToken).body(request).contentType(ContentType.JSON)
                .when().post("/rooms")
                .then().assertThat().statusCode(201).extract().as(RoomResponse.class);
        //@formatter:on
    }

}
