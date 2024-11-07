package corea.alarm.controller;

import corea.alarm.dto.AlarmCheckRequest;
import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.dto.AlarmResponses;
import corea.alarm.service.AlarmService;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AlarmController implements AlarmControllerSpecification {

    private final AlarmService alarmService;

    @GetMapping("/alarm/count")
    public ResponseEntity<AlarmCountResponse> getAlarmCount(@LoginMember AuthInfo authInfo) {
        AlarmCountResponse response = alarmService.getUnReadAlarmCount(authInfo.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/alarm")
    public ResponseEntity<AlarmResponses> getAlarms(@LoginMember AuthInfo authInfo) {
        AlarmResponses responses = alarmService.getAlarm(authInfo.getId());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/alarm/check")
    public ResponseEntity<Void> checkAlarm(@LoginMember AuthInfo authInfo, @RequestBody AlarmCheckRequest request) {
        alarmService.checkAlarm(authInfo.getId(), request);
        return ResponseEntity.ok()
                .build();
    }
}
