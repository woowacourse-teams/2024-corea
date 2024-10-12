package corea.matchresult.domain;

import corea.exception.ExceptionType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MatchingFailedReason {

    ROOM_NOT_FOUND(ExceptionType.ROOM_NOT_FOUND, "기존에 존재하던 방이 방장의 삭제로 인해 더 이상 유효하지 않아 매칭이 진행되지 않았습니다."),
    ROOM_STATUS_INVALID(ExceptionType.ROOM_STATUS_INVALID, "방이 이미 매칭 중이거나, 매칭이 완료되어 더 이상 매칭을 진행할 수 없는 상태입니다."),

    PARTICIPANT_SIZE_LACK(ExceptionType.PARTICIPANT_SIZE_LACK, "방의 최소 참여 인원보다 참가자가 부족하여 매칭이 진행되지 않았습니다."),
    PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST(ExceptionType.PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST, "참가자의 수는 최소 인원을 충족하였지만, 일부 참가자가 pull request를 제출하지 않아 매칭이 진행되지 않았습니다."),

    AUTOMATIC_MATCHING_NOT_FOUND(ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND, "해당 방에 대해 예약된 자동 매칭 시간이 존재하지 않거나 설정되지 않아 매칭이 진행되지 않았습니다."),

    UNKNOWN(null, "매칭 과정에서 오류가 발생하여 매칭이 진행되지 않았습니다. 문제가 지속될 경우 관리자에게 문의하세요."),
    ;

    private final ExceptionType exceptionType;
    private final String message;

    MatchingFailedReason(ExceptionType exceptionType, String message) {
        this.exceptionType = exceptionType;
        this.message = message;
    }

    public static MatchingFailedReason from(ExceptionType exceptionType) {
        return Arrays.stream(values())
                .filter(reason -> reason.isTypeMatching(exceptionType))
                .findAny()
                .orElse(UNKNOWN);
    }

    private boolean isTypeMatching(ExceptionType exceptionType) {
        return this.exceptionType == exceptionType;
    }
}
