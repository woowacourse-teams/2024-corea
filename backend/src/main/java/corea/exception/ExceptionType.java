package corea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    ALREADY_PARTICIPATED_ROOM(HttpStatus.BAD_REQUEST, "해당 방에 이미 참여했습니다."),
    NOT_PARTICIPATED_ROOM(HttpStatus.BAD_REQUEST, "아직 참여하지 않은 방입니다."),
    ROOM_STATUS_IS_NOT_PROGRESS(HttpStatus.BAD_REQUEST,"방이 진행중인 상태가 아닙니다."),
    ROOM_STATUS_INVALID(HttpStatus.BAD_REQUEST, "방이 마감되었습니다."),
    MEMBER_IS_NOT_MANAGER(HttpStatus.BAD_REQUEST, "매니저가 아닙니다."),
    MEMBER_IS_NOT_REVIEWER(HttpStatus.BAD_REQUEST, "리뷰어로만 참여할 수 없습니다."),
    MEMBER_IS_NOT_BOTH(HttpStatus.BAD_REQUEST, "리뷰어로만 참여할 수 있습니다."),
    ROOM_PARTICIPANT_EXCEED(HttpStatus.BAD_REQUEST, "방 참여 인원 수가 최대입니다."),
    PARTICIPANT_SIZE_LACK(HttpStatus.BAD_REQUEST, "참여 인원이 부족하여 매칭을 진행할 수 없습니다."),
    PARTICIPANT_SIZE_LACK_DUE_TO_PULL_REQUEST(HttpStatus.BAD_REQUEST, "pull request 미제출로 인해 인원이 부족하여 매칭을 진행할 수 없습니다."),
    NOT_MATCHED_MEMBER(HttpStatus.BAD_REQUEST, "매칭된 인원들이 아닙니다."),
    ALREADY_COMPLETED_REVIEW(HttpStatus.BAD_REQUEST, "이미 리뷰를 완료했습니다."),

    NOT_RECEIVED_ALARM(HttpStatus.BAD_REQUEST,"본인이 받은 알람이 아닙니다."),
    SAME_UNREAD_ALARM_EXIST(HttpStatus.BAD_REQUEST, "상대방에게 읽지 않은 같은 알람이 존재합니다."),

    ALREADY_COMPLETED_FEEDBACK(HttpStatus.BAD_REQUEST, "이미 작성한 피드백이 존재합니다."),
    INVALID_CALCULATION_FORMULA(HttpStatus.BAD_REQUEST, "잘못된 계산식입니다."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "올바르지 않은 값입니다."),
    INVALID_RECRUITMENT_DEADLINE(HttpStatus.BAD_REQUEST, "모집 마감일은 현재 시간 이후여야 합니다."),
    INVALID_REVIEW_DEADLINE(HttpStatus.BAD_REQUEST, "리뷰 마감일은 모집 마감일 이후여야 합니다."),
    INVALID_PULL_REQUEST_URL(HttpStatus.BAD_REQUEST, "올바르지 않은 풀 리퀘스트 주소입니다."),
    NOT_COMPLETE_GITHUB_REVIEW(HttpStatus.BAD_REQUEST, "깃허브 리뷰를 완료하지 않았습니다."),

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    AUTHORIZATION_ERROR(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ROOM_MODIFY_AUTHORIZATION_ERROR(HttpStatus.UNAUTHORIZED, "방 정보 변경 권한이 없습니다. 방 생성자만 방 정보를 변경할 수 있습니다."),
    FEEDBACK_UPDATE_AUTHORIZATION_ERROR(HttpStatus.UNAUTHORIZED, "피드백 수정 권한이 없습니다. 피드백 작성자만 피드백을 수정할 수 있습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 멤버를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방을 찾을 수 없습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "해당하는 값이 없습니다."),
    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "피드백을 찾을 수 없습니다."),
    COOKIE_NOT_EXIST(HttpStatus.NOT_FOUND, "해당 쿠키를 찾을 수 없습니다."),
    AUTOMATIC_MATCHING_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 방에 예약된 자동 매칭을 찾을 수 없습니다."),
    AUTOMATIC_UPDATE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 방에 예약된 자동 업데이트 정보를 찾을 수 없습니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    GITHUB_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "깃허브 서버가 원활하게 작동하지 않습니다."),
    GITHUB_AUTHORIZATION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "깃허브 인증 서버가 원활하게 작동하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionType(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
