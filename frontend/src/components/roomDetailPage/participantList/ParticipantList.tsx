import useCountDown from "@/hooks/common/useCountDown";
import { useFetchParticipantList } from "@/hooks/queries/useFetchRooms";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/roomDetailPage/participantList/ParticipantList.style";
import { RoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { STANDARD_PARTICIPANTS } from "@/constants/room";
import { HoverStyledLink } from "@/styles/common";

interface ParticipantListProps {
  roomInfo: RoomInfo;
}

const ParticipantList = ({ roomInfo }: ParticipantListProps) => {
  const isOpenStatus = roomInfo.roomStatus === "OPEN";
  const { data: participantListInfo, refetch } = useFetchParticipantList(roomInfo.id, isOpenStatus);
  const { remainingTime, isRefreshing, startRefreshCountDown } = useCountDown(5);

  const handleRefresh = () => {
    if (!isOpenStatus && !isRefreshing) {
      refetch();
      startRefreshCountDown();
    }
  };

  // 매칭 전 보여줄 화면
  if (isOpenStatus) {
    return (
      <S.TotalContainer>
        <S.MessageWrapper>{MESSAGES.GUIDANCE.EMPTY_PARTICIPANTS}</S.MessageWrapper>
      </S.TotalContainer>
    );
  }

  // 방 종료 후 참여자 없을 때 보여줄 화면
  if (roomInfo.roomStatus === "CLOSE" && participantListInfo.size === 0) {
    return (
      <S.TotalContainer>
        <S.MessageWrapper>{MESSAGES.GUIDANCE.ZERO_PARTICIPANTS}</S.MessageWrapper>
      </S.TotalContainer>
    );
  }

  // 방 종료 후 매칭 실패 상태의 화면
  if (roomInfo.roomStatus === "CLOSE" && roomInfo.message !== "") {
    return (
      <S.TotalContainer>
        <S.MessageWrapper>{MESSAGES.GUIDANCE.NO_PARTICIPANTS}</S.MessageWrapper>
      </S.TotalContainer>
    );
  }

  // 참여자 보여주는 화면
  return (
    <S.TotalContainer>
      {participantListInfo.size > STANDARD_PARTICIPANTS && (
        <S.RenewButtonWrapper>
          <Button
            onClick={handleRefresh}
            size="xSmall"
            disabled={isRefreshing}
            aria-label={
              isRefreshing ? `새로고침 가능까지 ${remainingTime}초 남았습니다` : "새로고침"
            }
          >
            {isRefreshing ? (
              <S.RefreshRemainTime>{remainingTime}</S.RefreshRemainTime>
            ) : (
              <Icon kind="arrowRenew" size="2rem" />
            )}
          </Button>
        </S.RenewButtonWrapper>
      )}

      <S.ParticipantListContainer>
        {participantListInfo.participants.map((participant) => (
          <S.ParticipantInfo key={participant.githubId}>
            <HoverStyledLink
              to={`/profile/${participant.username}`}
              aria-label={`${participant.username}. 클릭하면 프로필로 이동합니다.`}
            >
              <S.ProfileWrapper aria-hidden>
                <Profile imgSrc={participant.thumbnailLink} size={80} />
                <S.ProfileNickname>{participant.username}</S.ProfileNickname>
              </S.ProfileWrapper>
            </HoverStyledLink>

            <HoverStyledLink
              to={participant.prLink}
              target="_blank"
              rel="noreferrer"
              aria-label={`클릭하면 PR 링크로 이동합니다.`}
            >
              <S.PRLink>
                <Icon kind="link" size="1.8rem" />
                PR 링크
              </S.PRLink>
            </HoverStyledLink>
          </S.ParticipantInfo>
        ))}
      </S.ParticipantListContainer>
    </S.TotalContainer>
  );
};

export default ParticipantList;
