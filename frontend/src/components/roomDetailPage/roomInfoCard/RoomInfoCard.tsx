import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard.style";
import { RoomInfo } from "@/@types/roomInfo";
import { formatDateTimeString } from "@/utils/dateFormatter";

const RoomInfoCard = ({ roomInfo }: { roomInfo: RoomInfo }) => {
  return (
    <S.RoomInfoCardContainer>
      <S.RoomInfoCardImg src={roomInfo.thumbnailLink} alt="방 썸네일 이미지" />
      <S.RoomInfoCardContent>
        <S.RoomHeaderWrapper>
          <S.RoomTitle>{roomInfo.title}</S.RoomTitle>
          <S.RepositoryLink href={roomInfo.repositoryLink}>
            <Icon kind="link" />
            저장소 바로가기
          </S.RepositoryLink>
        </S.RoomHeaderWrapper>

        <S.RoomContentBox>
          <S.RoomTagBox>
            {roomInfo.keywords.map((keyword, index) => (
              <S.RoomKeyword key={keyword}>
                <S.RoomContentSmall>#{keyword}</S.RoomContentSmall>
              </S.RoomKeyword>
            ))}
          </S.RoomTagBox>
          <S.RoomContentSmall>{roomInfo.content}</S.RoomContentSmall>
        </S.RoomContentBox>

        <S.RoomContentBox>
          <S.RoomContentSmall>
            <Icon kind="person" />방 생성자 : {roomInfo.manager}
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" />
            현재 참여 인원 : {roomInfo.currentParticipants} / {roomInfo.limitedParticipants}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" />
            상호 리뷰 인원 : {roomInfo.matchingSize}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="calendar" />
            모집 마감일: {formatDateTimeString(roomInfo.recruitmentDeadline)}
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="calendar" />
            리뷰 마감일: {formatDateTimeString(roomInfo.reviewDeadline)}
          </S.RoomContentSmall>
        </S.RoomContentBox>
      </S.RoomInfoCardContent>
    </S.RoomInfoCardContainer>
  );
};

export default RoomInfoCard;
