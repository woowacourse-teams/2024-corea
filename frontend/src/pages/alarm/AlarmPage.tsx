import { useNavigate } from "react-router-dom";
import useMutateAlarm from "@/hooks/mutations/useMutateAlarm";
import { useFetchAlarmList } from "@/hooks/queries/useFetchAlarm";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/pages/alarm/AlarmPage.style";
import { AlarmActionType, AlarmItemData } from "@/@types/alaram";
import { mainLogo, thinkingCharacter } from "@/assets";
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

  const getAlarmMessage = (actionType: AlarmActionType, info: string, username?: string) => {
    if (actionType === "REVIEW_COMPLETE") {
      return (
        <>
          <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>코드리뷰를 완료</span>
          했습니다.
        </>
      );
    }

    if (actionType === "REVIEW_URGE") {
      return (
        <>
          <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>코드리뷰를 재촉</span>
          했습니다.
        </>
      );
    }

    if (actionType === "MATCH_COMPLETE") {
      return (
        <>
          <span>{info}</span> 미션에 대해 <span>매칭이 완료</span> 됐습니다.
        </>
      );
    }

    if (actionType === "MATCH_FAIL") {
      return (
        <>
          <span>{info}</span> 미션에 대해 <span>매칭이 실패</span> 했습니다.
        </>
      );
    }

    if (actionType === "FEEDBACK_CREATED") {
      return (
        <>
          <span>{username}</span> 님이 <span>{info}</span> 미션에 대해 <span>피드백을 작성</span>
          했습니다.
        </>
      );
    }

    return "";
  };

  const getNavigationPath = (actionType: AlarmActionType, interactionId: number) => {
    if (actionType === "REVIEW_COMPLETE") {
      return `/rooms/${interactionId}`;
    }

    if (actionType === "REVIEW_URGE") {
      return `/rooms/${interactionId}`;
    }

    if (actionType === "MATCH_COMPLETE") {
      return `/rooms/${interactionId}`;
    }

    if (actionType === "MATCH_FAIL") {
      return `/rooms/${interactionId}`;
    }

    if (actionType === "FEEDBACK_CREATED") {
      return `/feedback`;
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
      <ContentSection title="알림 내역">
        <S.AlarmList>
          {alarmListData?.map((alarm) => (
            <S.AlarmItem
              key={alarm.alarmId}
              $isRead={alarm.isRead}
              onClick={() => handleAlarmClick(alarm)}
            >
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
          ))}
        </S.AlarmList>
      </ContentSection>
    </S.Layout>
  );
};

export default AlarmPage;
