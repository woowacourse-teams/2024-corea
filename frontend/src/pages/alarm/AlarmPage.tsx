import { useNavigate } from "react-router-dom";
import { useFetchAlarmList } from "@/hooks/queries/useFetchAlarm";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/pages/alarm/AlarmPage.style";
import { formatTimeAgo } from "@/utils/dateFormatter";

const AlarmPage = () => {
  const navigate = useNavigate();
  const { data } = useFetchAlarmList();
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

  const handleAlarmClick = (actionType: string, interactionId: number) => {
    const path = getNavigationPath(actionType, interactionId);
    if (path) {
      navigate(path);
    }
  };

  //TODO: 알람이 없을 때

  return (
    <S.Layout>
      <S.AlarmList>
        {alarmListData?.map((alarm) => (
          <S.AlarmItem
            key={alarm.alarmId}
            $isRead={alarm.isRead}
            onClick={() => handleAlarmClick(alarm.actionType, alarm.interaction.interactionId)}
          >
            {alarm.actor && (
              <S.ProfileWrapper>
                <Profile imgSrc={alarm.actor.thumbnailUrl} size={40} />
              </S.ProfileWrapper>
            )}
            <S.ContentWrapper>
              <S.Content>
                {getAlarmMessage(alarm.actionType, alarm.actor.username, alarm.interaction.info)}
              </S.Content>
              <S.TimeStamp>{formatTimeAgo(alarm.createAt)}</S.TimeStamp>
            </S.ContentWrapper>
          </S.AlarmItem>
        ))}
      </S.AlarmList>
    </S.Layout>
  );
};

export default AlarmPage;
