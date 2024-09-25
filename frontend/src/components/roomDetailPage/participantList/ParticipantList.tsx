import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import Profile from "@/components/common/profile/Profile";
import * as S from "@/components/roomDetailPage/participantList/ParticipantList.style";
import { ParticipantListInfo } from "@/@types/participantList";

interface ParticipantListProps extends ParticipantListInfo {
  onRefresh: () => void;
}

const ParticipantList = ({ participants, onRefresh }: ParticipantListProps) => {
  return (
    <S.TotalContainer>
      <S.RenewButtonWrapper>
        <Button onClick={onRefresh} size="icon">
          {<Icon kind="arrowRenew" size={20} />}
        </Button>
      </S.RenewButtonWrapper>

      <S.ParticipantListContainer>
        {participants.map((participant) => (
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
