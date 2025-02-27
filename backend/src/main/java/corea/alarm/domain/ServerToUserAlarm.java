package corea.alarm.domain;

import corea.global.BaseTimeEntity;
import corea.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ServerToUserAlarm extends BaseTimeEntity implements Alarm {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmActionType alarmActionType;

    private Long receiverId;

    private Long interactionId;

    private boolean isRead;

    public ServerToUserAlarm(AlarmActionType alarmActionType, long receiverId, long interactionId, boolean isRead) {
        this(null, alarmActionType, receiverId, interactionId, isRead);
    }

    @Override
    public boolean isStatus(boolean status) {
        return isRead == status;
    }

    @Override
    public String getActionType() {
        return alarmActionType.name();
    }

    @Override
    public Long getActorId() {
        return null;
    }

    @Override
    public boolean isNotReceiver(Member member) {
        return !receiverId.equals(member.getId());
    }

    @Override
    public void read() {
        isRead = true;
    }
}
