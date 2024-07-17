import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/shared/roomCard/RoomCard.style";
import { RoomInfo } from "@/@types/roomInfo";

const RoomCard = ({ roomInfo }: { roomInfo: RoomInfo }) => {
  return (
    <S.RoomCardContainer>
      <S.RoomInfoThumbnail src={roomInfo.thumbnailLink} alt={roomInfo.title} />
      <S.RoomInformation>
        <S.MainInfo>
          <S.RoomTitle>{roomInfo.title}</S.RoomTitle>
          <S.RecruitmentDeadline>
            <Icon kind="calendar" />
            {roomInfo.recruitmentDeadline}
          </S.RecruitmentDeadline>
        </S.MainInfo>
        <S.EtcInfo>
          <S.KeywordsContainer>
            {roomInfo.keywords.map((keyword) => (
              <S.RoomKeyword key={keyword}>#{keyword}</S.RoomKeyword>
            ))}
          </S.KeywordsContainer>
          <div>
            <Icon kind="person" />
            {roomInfo.currentParticipantSize}/{roomInfo.maximumParticipantSize}
          </div>
        </S.EtcInfo>
      </S.RoomInformation>
    </S.RoomCardContainer>
  );
};

export default RoomCard;
