package corea.alarm.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToUserAlarmRepository extends JpaRepository<UserToUserAlarm, Long> {

    List<UserToUserAlarm> findAllByReceiverId(Long receiverId);
}
