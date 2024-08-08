package corea.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "깃허브 소셜 로그인을 위한 액세스 토큰 요청")
public record GithubAuthRequest(@Schema(description = "클라이언트 아이디", example = "O1234567COREACLIENT")
                                @NotBlank
                                @JsonProperty("client_id")
                                String clientId,

                                @Schema(description = "클라이언트 비밀키", example = "qwer1234zxcvasdf")
                                @NotBlank
                                @JsonProperty("client_secret")
                                String clientSecret,

                                @Schema(description = "인증 코드", example = "4csdf1234asdf")
                                @NotBlank
                                String code) {
}
