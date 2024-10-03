package corea.bdd;

import corea.auth.dto.GithubUserInfo;
import corea.auth.dto.LoginRequest;
import corea.auth.service.GithubOAuthProvider;
import corea.member.domain.Member;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.mockito.Mockito;

public class MemberGiven {

    public static String 멤버_로그인(GithubOAuthProvider githubOAuthProvider, Member member) {
        String mockingCode = member.getGithubUserId();
        Mockito.when(githubOAuthProvider.getUserInfo(mockingCode))
                .thenReturn(new GithubUserInfo(
                        member.getUsername(),
                        member.getName(),
                        member.getThumbnailUrl(),
                        member.getEmail(),
                        member.getGithubUserId()
                ));
        //@formatter:off
        return RestAssured.given().body(new LoginRequest(mockingCode)).contentType(ContentType.JSON)
                .when().post("/login")
                .then().statusCode(200).extract().header("Authorization");
        //@formatter:on
    }
}
