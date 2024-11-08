import { useNavigate } from "react-router-dom";
import useMutateAlarm from "@/hooks/mutations/useMutateAlarm";
import { useFetchAlarmList } from "@/hooks/queries/useFetchAlarm";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/pages/alarm/AlarmPage.style";
import { AlarmItemData } from "@/@types/alaram";
import { thinkingCharacter } from "@/assets";
import MESSAGES from "@/constants/message";
import { formatTimeAgo } from "@/utils/dateFormatter";

const EmptyAlarm = () => {
  return (
    <S.EmptyContainer>
      <S.GuidanceWrapper>
        <S.Character src={thinkingCharacter} alt="생각 중인 캐릭터" />
        <p>{MESSAGES.GUIDANCE.NO_ALARM_LIST}</p>
      </S.GuidanceWrapper>
    </S.EmptyContainer>
  );
};

const AlarmPage = () => {
  const navigate = useNavigate();
  const { data } = useFetchAlarmList();
  const { markAsRead } = useMutateAlarm();
  const alarmListData = data?.data;

  const getAlarmMessage = (actionType: string, username: string, info: string) => {
    if (actionType === "REVIEW_COMPLETE") {
      return (
        <>
          <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 코드리뷰를 완료했습니다.
        </>
      );
    }
    return "";
  };

  const getNavigationPath = (actionType: string, interactionId: number) => {
    if (actionType === "REVIEW_COMPLETE") {
      return `/rooms/${interactionId}`;
    }
    return "";
  };

  const handleAlarmClick = async (alarm: AlarmItemData) => {
    const { actionType, alarmId, isRead, interaction } = alarm;

    if (!isRead) {
      await markAsRead.mutateAsync({ alarmId, alarmType: "USER" });
    }

    const path = getNavigationPath(actionType, interaction.interactionId);
    if (path) {
      navigate(path);
    }
  };

  if (!alarmListData?.length) {
    return <EmptyAlarm />;
  }

  return (
    <S.Layout>
      <S.AlarmList>
        {alarmListData?.map((alarm) => (
          <S.AlarmItem
            key={alarm.alarmId}
            $isRead={alarm.isRead}
            onClick={() => handleAlarmClick(alarm)}
          >
            {alarm.actor && (
              <S.ProfileWrapper>
                <Profile imgSrc={alarm.actor.thumbnailUrl} size={40} />
              </S.ProfileWrapper>
            )}
            <S.ContentWrapper>
              <S.Content $isRead={alarm.isRead}>
                {getAlarmMessage(alarm.actionType, alarm.actor.username, alarm.interaction.info)}
              </S.Content>
              <S.TimeStamp $isRead={alarm.isRead}>{formatTimeAgo(alarm.createAt)}</S.TimeStamp>
            </S.ContentWrapper>
          </S.AlarmItem>
        ))}
      </S.AlarmList>
    </S.Layout>
  );
};

export default AlarmPage;
