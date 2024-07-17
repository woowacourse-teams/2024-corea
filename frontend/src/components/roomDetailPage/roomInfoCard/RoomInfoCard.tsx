import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard.style";
import { RoomInfo } from "@/@types/roomInfo";
import { getRoomDetailInfo } from "@/apis/rooms.api";

const RoomInfoCard = () => {
  const [roomInfo, setRoomInfo] = useState<RoomInfo>();
  const params = useParams();
  const missionId = params.id ? Number(params.id) : 0;

  const fetchRoomDetailInfoData = async () => {
    const res = await getRoomDetailInfo(missionId);
    setRoomInfo(res);
  };

  useEffect(() => {
    fetchRoomDetailInfoData();
  }, []);

  if (!roomInfo) return <></>;

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
              <S.RoomKeyword>
                <S.RoomContentSmall key={index}>#{keyword}</S.RoomContentSmall>
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
            현재 참여 인원 : {roomInfo.currentParticipantSize} / {roomInfo.maximumParticipantSize}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" />
            상호 리뷰 인원 : {roomInfo.matchingSize}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="calendar" />
            모집 마감일: {roomInfo.recruitmentDeadline}
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="calendar" />
            리뷰 마감일: {roomInfo.reviewDeadline}
          </S.RoomContentSmall>
        </S.RoomContentBox>
      </S.RoomInfoCardContent>
    </S.RoomInfoCardContainer>
  );
};

export default RoomInfoCard;
