package corea.member.dto;

import corea.member.domain.AuthRole;

public record MemberRoleResponse(String role) {

    public static MemberRoleResponse from(boolean isReviewer) {
        AuthRole role = isReviewer ? AuthRole.REVIEWER : AuthRole.REVIEWEE;
        return new MemberRoleResponse(role.name());
    }
}
