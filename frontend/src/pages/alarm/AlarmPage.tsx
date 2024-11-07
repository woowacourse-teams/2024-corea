import { useFetchAlarmList } from "@/hooks/queries/useFetchAlarm";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/pages/alarm/AlarmPage.style";

const AlarmPage = () => {
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

  return (
    <S.Layout>
      <S.AlarmList>
        {alarmListData?.map((alarm) => (
          <S.AlarmItem key={alarm.alarmId}>
            {alarm.actor && (
              <S.ProfileWrapper>
                <Profile imgSrc={alarm.actor.thumbnailUrl} size={40} />
              </S.ProfileWrapper>
            )}
            <S.ContentWrapper>
              <S.Content>
                {getAlarmMessage(alarm.actionType, alarm.actor.username, alarm.interaction.info)}
              </S.Content>
              <S.TimeStamp>{alarm.createAt}</S.TimeStamp>
            </S.ContentWrapper>
          </S.AlarmItem>
        ))}
      </S.AlarmList>
    </S.Layout>
  );
};

export default AlarmPage;
