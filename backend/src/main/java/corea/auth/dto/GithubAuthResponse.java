package corea.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "깃허브 소셜 로그인을 위한 액세스 토큰 응답")
public record GithubAuthResponse(@Schema(description = "액세스 토큰", example = "O1234567COREACLIENT")
                                 @NotBlank
                                 @JsonProperty("access_token")
                                 String accessToken,

                                 @Schema(description = "허용 스코프", example = "user:email")
                                 String scope,

                                 @Schema(description = "토큰 타입", example = "bearer")
                                 @JsonProperty("token_type")
                                 String tokenType) {
}
