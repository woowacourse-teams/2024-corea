import * as S from "@/components/alarm/alarmItem/AlarmItem.style";
import Profile from "@/components/common/profile/Profile";
import { AlarmItemData } from "@/@types/alarm";
import { mainLogo } from "@/assets";
import ALARM_MESSAGES from "@/constants/alarm";
import { formatTimeAgo } from "@/utils/dateFormatter";

interface AlarmItemProps {
  alarm: AlarmItemData;
  onAlarmClick: (alarm: AlarmItemData) => void;
}

const AlarmItem = ({ alarm, onAlarmClick }: AlarmItemProps) => {
  const { alarmId, actor, isRead, actionType, interaction, createAt } = alarm;

  const getAlarmMessage = () => {
    const message = ALARM_MESSAGES[actionType as keyof typeof ALARM_MESSAGES];
    if (!message) return <></>;

    return message({
      username: actor?.username,
      info: interaction.info,
    });
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
