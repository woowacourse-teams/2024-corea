/* eslint-disable react/display-name */
import React from "react";
import useModal from "@/hooks/common/useModal";
import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import * as S from "@/components/shared/roomCard/RoomCard.style";
import RoomCardModal from "@/components/shared/roomCardModal/RoomCardModal";
import { RoomInfo } from "@/@types/roomInfo";
import { MAX_KEYWORDS } from "@/constants/room";
import { theme } from "@/styles/theme";
import { formatDday, formatLeftTime } from "@/utils/dateFormatter";

const DisplayLeftTime = (roomInfo: RoomInfo) => {
  if (roomInfo.roomStatus === "OPEN") {
    const dDay = formatDday(roomInfo.recruitmentDeadline);
    const leftTime = formatLeftTime(roomInfo.recruitmentDeadline);

    return (
      <>
        {dDay !== "종료됨" && "모집 마감"}
        <S.StyledDday>{dDay === "D-Day" ? leftTime : dDay}</S.StyledDday>
      </>
    );
  }

  if (roomInfo.roomStatus === "PROGRESS") {
    const dDay = formatDday(roomInfo.reviewDeadline);
    const leftTime = formatLeftTime(roomInfo.reviewDeadline);

    return (
      <>
        {dDay !== "종료됨" && "리뷰 마감"}
        <S.StyledDday>{dDay ? leftTime : dDay}</S.StyledDday>
      </>
    );
  }

  return <>종료됨</>;
};

interface RoomCardProps {
  roomInfo: RoomInfo;
}

const RoomCard = React.memo(({ roomInfo }: RoomCardProps) => {
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();

  const displayedKeywords = roomInfo.keywords
    .filter((keyword) => keyword !== "")
    .slice(0, MAX_KEYWORDS);

  return (
    <>
      <RoomCardModal isOpen={isModalOpen} onClose={handleCloseModal} roomInfo={roomInfo} />

      <S.RoomCardContainer onClick={handleOpenModal}>
        <S.RoomInfoThumbnail
          as={ImageWithFallback}
          src={roomInfo.thumbnailLink}
          alt={roomInfo.title}
        />
        <S.RoomInformation>
          <S.RoomTitle>{roomInfo.title}</S.RoomTitle>

          <S.KeywordsContainer>
            <S.KeywordWrapper>
              {displayedKeywords.length === 0 ? (
                <S.NoKeywordText>지정된 키워드 없음</S.NoKeywordText>
              ) : (
                displayedKeywords.map((keyword) => (
                  <S.KeywordText key={keyword}>#{keyword}</S.KeywordText>
                ))
              )}
            </S.KeywordWrapper>
          </S.KeywordsContainer>

          <S.EtcContainer>
            <Label type={roomInfo.roomStatus} />
            <S.JoinMember>
              <Icon kind="person" size="1.6rem" color={theme.COLOR.grey4} />
              {roomInfo.currentParticipants}/{roomInfo.limitedParticipants}
            </S.JoinMember>
          </S.EtcContainer>

          <S.DeadLineText>{DisplayLeftTime(roomInfo)}</S.DeadLineText>
        </S.RoomInformation>
      </S.RoomCardContainer>
    </>
  );
});

export default RoomCard;
