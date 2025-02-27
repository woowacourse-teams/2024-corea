package corea.alarm.repository;

import corea.alarm.domain.ServerToUserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerToUserAlarmRepository extends JpaRepository<ServerToUserAlarm, Long> {

    List<ServerToUserAlarm> findAllByReceiverId(long receiverId);
}
