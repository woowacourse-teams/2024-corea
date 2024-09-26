import { Link } from "react-router-dom";
import { useFetchParticipantList } from "@/hooks/queries/useFetchRooms";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/roomDetailPage/participantList/ParticipantList.style";

interface ParticipantListProps {
  roomId: number;
}

const ParticipantList = ({ roomId }: ParticipantListProps) => {
  const { data: participantListInfo, refetch } = useFetchParticipantList(roomId);

  const handleRefresh = () => {
    refetch();
  };

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
            <Link to={`/profile/${participant.username}`}>
              <S.ProfileWrapper>
                <Profile imgSrc={participant.thumbnailLink} size={80} />
                <S.ProfileNickname>{participant.username}</S.ProfileNickname>
              </S.ProfileWrapper>
            </Link>
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
