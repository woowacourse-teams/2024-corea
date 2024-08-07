package corea.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record GithubAuthResponse(
        @NotBlank
        @JsonProperty("access_token")
        String accessToken,
        String scope,
        String token_type
) {
}
