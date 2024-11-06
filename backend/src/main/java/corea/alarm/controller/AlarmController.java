package corea.alarm.controller;

import corea.alarm.dto.AlarmCountResponse;
import corea.alarm.service.AlarmService;
import corea.auth.annotation.LoginMember;
import corea.auth.domain.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AlarmController implements AlarmControllerSpecification{

    private final AlarmService alarmService;

    @GetMapping("/alarm/count")
    public ResponseEntity<AlarmCountResponse> getAlarmCount(@LoginMember AuthInfo authInfo) {
        AlarmCountResponse response = alarmService.getNotReadAlarmCount(authInfo.getId());
        return ResponseEntity.ok(response);
    }
}
