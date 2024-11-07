package corea.alarm.dto;

import corea.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;


public record MemberResponse(
        @Schema(description = "멤버 아이디", example = "2")
        long memberId,

        @Schema(description = "유저이름", example = "ashsty")
        String username,

        @Schema(description = "프로필 이미지", example = "www.myProfile.jpg")
        String thumbnailUrl
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getThumbnailUrl());
    }
}
