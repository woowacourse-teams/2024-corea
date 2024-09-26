import { useFetchParticipantList } from "@/hooks/queries/useFetchRooms";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/roomDetailPage/participantList/ParticipantList.style";
import { RoomInfo } from "@/@types/roomInfo";

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

  if (isOpenStatus) {
    return (
      <S.TotalContainer>
        <S.MessageWrapper>참여자 목록은 매칭이 시작된 이후 공개됩니다.</S.MessageWrapper>
      </S.TotalContainer>
    );
  }

  return (
    <S.TotalContainer>
      <S.RenewButtonWrapper>
        <Button onClick={handleRefresh} size="xSmall">
          <Icon kind="arrowRenew" size={20} />
        </Button>
      </S.RenewButtonWrapper>

      <S.ParticipantListContainer>
        {participantListInfo.participants.map((participant) => (
          <S.ParticipantInfo key={participant.githubId}>
            <S.ProfileWrapper>
              <Profile imgSrc={participant.thumbnailLink} size={80} />
              <S.ProfileNickname>{participant.username}</S.ProfileNickname>
            </S.ProfileWrapper>
            <S.PRLink href={participant.prLink} target="_blank">
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
