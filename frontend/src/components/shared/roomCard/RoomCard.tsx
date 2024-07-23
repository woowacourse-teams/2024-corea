import React from "react";
import Icon from "@/components/common/icon/Icon";
import Label from "@/components/common/label/Label";
import * as S from "@/components/shared/roomCard/RoomCard.style";
import { RoomInfo } from "@/@types/roomInfo";
import { MAX_KEYWORDS } from "@/constants/room";
import { formatDateString } from "@/utils/dateFormatter";

const RoomCard = ({ roomInfo }: { roomInfo: RoomInfo }) => {
  const displayedKeywords = roomInfo.keywords.slice(0, MAX_KEYWORDS);
  const hasMoreKeywords = roomInfo.keywords.length > MAX_KEYWORDS;

  return (
    <S.RoomCardContainer>
      <S.RoomInfoThumbnail src={roomInfo.thumbnailLink} alt={roomInfo.title} />
      <S.RoomInformation>
        <S.RoomTitle>{roomInfo.title}</S.RoomTitle>
        <S.KeywordsContainer>
          {displayedKeywords.map((keyword) => (
            <Label key={keyword} type="keyword" text={keyword} />
          ))}
          {hasMoreKeywords && <S.MoreKeywords>...</S.MoreKeywords>}
        </S.KeywordsContainer>
        <S.EtcContainer>
          {roomInfo.isClosed ? (
            <Label type="close" text="마감됨" />
          ) : (
            <Label type="open" text="모집중" />
          )}
          <div>
            <Icon kind="person" />
            {roomInfo.currentParticipantSize}/{roomInfo.maximumParticipantSize} 명
          </div>
        </S.EtcContainer>
        {formatDateString(roomInfo.recruitmentDeadline)}
      </S.RoomInformation>
    </S.RoomCardContainer>
  );
};

export default RoomCard;
