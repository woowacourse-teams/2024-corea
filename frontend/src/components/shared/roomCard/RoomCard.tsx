import React from "react";
import useModal from "@/hooks/common/useModal";
import Icon from "@/components/common/icon/Icon";
import Label from "@/components/common/label/Label";
import * as S from "@/components/shared/roomCard/RoomCard.style";
import RoomCardModal from "@/components/shared/roomCardModal/RoomCardModal";
import { RoomInfo } from "@/@types/roomInfo";
import { MAX_KEYWORDS } from "@/constants/room";
import { formatDeadlineString } from "@/utils/dateFormatter";

interface RoomCardProps {
  roomInfo: RoomInfo;
}

const RoomCard = ({ roomInfo }: RoomCardProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();

  const displayedKeywords = roomInfo.keywords.slice(0, MAX_KEYWORDS);
  const hasMoreKeywords = roomInfo.keywords.length > MAX_KEYWORDS;

  return (
    <>
      <RoomCardModal isOpen={isOpen} onClose={handleCloseModal} roomInfo={roomInfo} />

      <S.RoomCardContainer onClick={handleOpenModal}>
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
              {roomInfo.currentParticipants}/{roomInfo.limitedParticipants} 명
            </div>
          </S.EtcContainer>
          {formatDeadlineString(roomInfo.recruitmentDeadline)}
        </S.RoomInformation>
      </S.RoomCardContainer>
    </>
  );
};

export default RoomCard;
