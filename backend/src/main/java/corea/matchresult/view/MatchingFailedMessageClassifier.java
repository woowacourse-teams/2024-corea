package corea.matchresult.view;

import corea.exception.ExceptionType;

import java.util.EnumMap;
import java.util.Map;

public class MatchingFailedMessageClassifier {

    private static final String ROOM_NOT_FOUND_MESSAGE = "기존에 존재하던 방이 방장의 삭제로 인해 더 이상 유효하지 않아 매칭을 진행할 수 없습니다.";
    private static final String ROOM_STATUS_INVALID_MESSAGE = "방이 이미 매칭 중이거나, 매칭이 완료되어 더 이상 매칭을 진행할 수 없는 상태입니다.";
    private static final String PARTICIPANT_SIZE_LACK_MESSAGE = "방의 최소 참여 인원보다 참가자가 부족하여 매칭을 시작할 수 없습니다. 더 많은 참가자가 필요합니다.";
    private static final String DUE_TO_PULL_REQUEST_MESSAGE = "참가자의 수는 최소 인원을 충족하였지만, 일부 참가자가 pull request를 제출하지 않아 매칭을 진행하기에 참가자가 부족합니다.";
    private static final String MATCHING_NOT_FOUND_MESSAGE = "해당 방에 대해 예약된 자동 매칭 시간이 존재하지 않거나 설정되지 않아 매칭을 진행할 수 없습니다.";
    private static final String DEFAULT_MATCHING_FAILED_MESSAGE = "매칭 과정에서 오류가 발생하여 매칭을 완료할 수 없습니다. 문제가 지속될 경우 관리자에게 문의하세요.";

    private static final Map<ExceptionType, String> MESSAGE_STORE = new EnumMap<>(ExceptionType.class);

    static {
        MESSAGE_STORE.put(ExceptionType.ROOM_NOT_FOUND, ROOM_NOT_FOUND_MESSAGE);
        MESSAGE_STORE.put(ExceptionType.ROOM_STATUS_INVALID, ROOM_STATUS_INVALID_MESSAGE);

        MESSAGE_STORE.put(ExceptionType.PARTICIPANT_SIZE_LACK, PARTICIPANT_SIZE_LACK_MESSAGE);
        MESSAGE_STORE.put(ExceptionType.PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST, DUE_TO_PULL_REQUEST_MESSAGE);

        MESSAGE_STORE.put(ExceptionType.AUTOMATIC_MATCHING_NOT_FOUND, MATCHING_NOT_FOUND_MESSAGE);
    }

    public static String classifyFailureMessage(ExceptionType exceptionType) {
        return MESSAGE_STORE.getOrDefault(exceptionType, DEFAULT_MATCHING_FAILED_MESSAGE);
    }
}
