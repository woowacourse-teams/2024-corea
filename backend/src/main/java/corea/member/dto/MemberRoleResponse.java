package corea.member.dto;

import corea.member.domain.Member;

public record MemberRoleResponse(String role) {
    public static MemberRoleResponse from(Member member) {
        return new MemberRoleResponse(member.getAuthRole()
                .name());
    }
}
