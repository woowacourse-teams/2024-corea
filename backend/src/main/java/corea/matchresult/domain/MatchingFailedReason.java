package corea.matchresult.domain;

import corea.exception.CoreaException;
import corea.exception.ExceptionType;

import java.util.Arrays;

public enum MatchingFailedReason {

    ROOM_NOT_FOUND(ExceptionType.ROOM_NOT_FOUND),
    ROOM_STATUS_INVALID(ExceptionType.ROOM_STATUS_INVALID),

    PARTICIPANT_SIZE_LACK(ExceptionType.PARTICIPANT_SIZE_LACK),
    PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST(ExceptionType.PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST),

    AUTOMATIC_MATCHING_NOT_FOUND(ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND),
    ;

    private final ExceptionType exceptionType;

    MatchingFailedReason(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public static MatchingFailedReason from(ExceptionType exceptionType) {
        return Arrays.stream(values())
                .filter(reason -> reason.isTypeMatching(exceptionType))
                .findAny()
                .orElseThrow(() -> new CoreaException(ExceptionType.NOT_FOUND_ERROR));
    }

    private boolean isTypeMatching(ExceptionType exceptionType) {
        return this.exceptionType == exceptionType;
    }
}
