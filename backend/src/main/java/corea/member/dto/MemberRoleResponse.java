package corea.member.dto;

import corea.member.domain.MemberRole;

public record MemberRoleResponse(String role) {

    public static MemberRoleResponse from(boolean isReviewer) {
        MemberRole role = isReviewer ? MemberRole.REVIEWER : MemberRole.REVIEWEE;
        return new MemberRoleResponse(role.name());
    }
}
