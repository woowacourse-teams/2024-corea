package corea.alarm.domain;

import corea.member.domain.Member;

public interface Alarm {

    boolean isStatus(boolean status);

    boolean isNotReceiver(Member member);

    void read();

    String getActionType();

    Long getId();

    Long getActorId();

    Long getInteractionId();

    Long getReceiverId();
}
