package corea.member.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;

public enum MemberRole {

    REVIEWER,
    REVIEWEE,
    BOTH,
    NONE,
    ;

    public static MemberRole from(String role) {
        try {
            return MemberRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CoreaException(ExceptionType.NOT_FOUND_ERROR);
        }
    }

    public boolean isReviewer() {
        return this == REVIEWER;
    }
}
