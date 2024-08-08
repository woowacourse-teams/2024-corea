package corea.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record GithubAuthRequest(
        @NotBlank
        @JsonProperty("client_id")
        String clientId,

        @NotBlank
        @JsonProperty("client_secret")
        String clientSecret,

        @NotBlank
        String code
) {
}
