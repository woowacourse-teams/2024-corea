import * as S from "@/components/alarm/alarmItem/AlarmItem.style";
import Profile from "@/components/common/profile/Profile";
import { AlarmItemData } from "@/@types/alaram";
import { mainLogo } from "@/assets";
import { formatTimeAgo } from "@/utils/dateFormatter";

const ALARM_MESSAGES_WITH_USERNAME = {
  REVIEW_COMPLETE: (username: string, info: string) => {
    return (
      <>
        <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>코드리뷰를 완료</span>
        했습니다.
      </>
    );
  },

  REVIEW_URGE: (username: string, info: string) => {
    return (
      <>
        <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>코드리뷰를 재촉</span>
        했습니다.
      </>
    );
  },

  FEEDBACK_CREATED: (username: string, info: string) => {
    return (
      <>
        <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>피드백을 작성</span>
        했습니다.
      </>
    );
  },
} as const;

const ALARM_MESSAGES_WITHOUT_USERNAME = {
  MATCH_COMPLETE: (info: string) => {
    return (
      <>
        <span>{info}</span> 미션에 대해 <span>매칭이 완료</span> 됐습니다.
      </>
    );
  },

  MATCH_FAIL: (info: string) => {
    return (
      <>
        <span>{info}</span> 미션에 대해 <span>매칭이 실패</span> 했습니다.
      </>
    );
  },
} as const;

interface AlarmItemProps {
  alarm: AlarmItemData;
  onAlarmClick: (alarm: AlarmItemData) => void;
}

const AlarmItem = ({ alarm, onAlarmClick }: AlarmItemProps) => {
  const { alarmId, actor, isRead, actionType, interaction, createAt } = alarm;

  const getAlarmMessage = () => {
    if (actionType === "MATCH_COMPLETE" || actionType === "MATCH_FAIL") {
      const message = ALARM_MESSAGES_WITHOUT_USERNAME[actionType];
      return message?.(interaction.info) ?? <></>;
    }

    const message = ALARM_MESSAGES_WITH_USERNAME[actionType];
    return message?.(actor?.username ?? "", interaction.info) ?? <></>;
  };

  return (
    <S.AlarmItem key={alarmId} $isRead={isRead} onClick={() => onAlarmClick(alarm)}>
      <S.ProfileWrapper>
        <Profile imgSrc={actor ? actor.thumbnailUrl : mainLogo} size={40} />
      </S.ProfileWrapper>
      <S.ContentWrapper>
        <S.Content $isRead={isRead}>{getAlarmMessage()}</S.Content>
        <S.TimeStamp $isRead={isRead}>{formatTimeAgo(createAt)}</S.TimeStamp>
      </S.ContentWrapper>
    </S.AlarmItem>
  );
};

export default AlarmItem;
