import * as S from "@/components/alarm/alarmItem/AlarmItem.style";
import Profile from "@/components/common/profile/Profile";
import { AlarmActionType, AlarmItemData } from "@/@types/alaram";
import { mainLogo } from "@/assets";
import { formatTimeAgo } from "@/utils/dateFormatter";

interface AlarmItemProps {
  alarm: AlarmItemData;
  onAlarmClick: (alarm: AlarmItemData) => void;
  getAlarmMessage: (actionType: AlarmActionType, info: string, username?: string) => JSX.Element;
}

const AlarmItem = ({ alarm, onAlarmClick, getAlarmMessage }: AlarmItemProps) => {
  return (
    <S.AlarmItem key={alarm.alarmId} $isRead={alarm.isRead} onClick={() => onAlarmClick(alarm)}>
      <S.ProfileWrapper>
        <Profile imgSrc={alarm.actor ? alarm.actor.thumbnailUrl : mainLogo} size={40} />
      </S.ProfileWrapper>
      <S.ContentWrapper>
        <S.Content $isRead={alarm.isRead}>
          {getAlarmMessage(alarm.actionType, alarm.interaction.info, alarm.actor?.username)}
        </S.Content>
        <S.TimeStamp $isRead={alarm.isRead}>{formatTimeAgo(alarm.createAt)}</S.TimeStamp>
      </S.ContentWrapper>
    </S.AlarmItem>
  );
};

export default AlarmItem;
