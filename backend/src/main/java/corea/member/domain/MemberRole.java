package corea.member.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;
import corea.participation.domain.ParticipationStatus;

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

    public ParticipationStatus getParticipationStatus() {
        return switch (this) {
            case REVIEWER, REVIEWEE, BOTH -> ParticipationStatus.PARTICIPATED;
            case NONE -> ParticipationStatus.NOT_PARTICIPATED;
        };
    }
}
