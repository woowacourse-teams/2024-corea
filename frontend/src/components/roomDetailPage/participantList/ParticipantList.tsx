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

  const handleRefresh = () => {
    if (!isOpenStatus) {
      refetch();
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

  // 참여자 보여주는 화면
  return (
    <S.TotalContainer>
      {participantListInfo.size > STANDARD_PARTICIPANTS && (
        <S.RenewButtonWrapper>
          <Button onClick={handleRefresh} size="xSmall">
            <Icon kind="arrowRenew" size="2rem" />
          </Button>
        </S.RenewButtonWrapper>
      )}

      <S.ParticipantListContainer>
        {participantListInfo.participants.map((participant) => (
          <S.ParticipantInfo key={participant.githubId}>
            <HoverStyledLink to={`/profile/${participant.username}`}>
              <S.ProfileWrapper>
                <Profile imgSrc={participant.thumbnailLink} size={80} />
                <S.ProfileNickname>{participant.username}</S.ProfileNickname>
              </S.ProfileWrapper>
            </HoverStyledLink>
            <S.PRLink href={participant.prLink} target="_blank" rel="noreferrer">
              <Icon kind="link" size="1.6rem" />
              PR 링크
            </S.PRLink>
          </S.ParticipantInfo>
        ))}
      </S.ParticipantListContainer>
    </S.TotalContainer>
  );
};

export default ParticipantList;
