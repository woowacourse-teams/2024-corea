package corea.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "깃허브에서 관리하는 유저 정보")
public record GithubUserInfo(@Schema(description = "아이디", example = "youngsu5582")
                             String login,

                             @Schema(description = "별명", example = "조희선")
                             String name,

                             @Schema(description = "프로필 사진 URL", example = "https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=13301655&filePath=L2Rpc2sxL25ld2RhdGEvMjAyMS8yMS9DTFMxMDAwNC8xMzMwMTY1NV9XUlRfMjFfQ0xTMTAwMDRfMjAyMTEyMTNfMQ==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004")
                             @JsonProperty("avatar_url")
                             String avatarUrl,

                             @Schema(description = "이메일", example = "corea@naver.com")
                             String email,

                             @Schema(description = "깃허브 ID", example = "98307410")
                             String githubUserId) {

    public GithubUserInfo {
        if (email == null) {
            email = "";
        }
    }
}
