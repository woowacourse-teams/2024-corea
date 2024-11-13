/* eslint-disable react/display-name */
import React, { useState } from "react";
import useModal from "@/hooks/common/useModal";
import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import ClassificationBadge from "@/components/shared/classificationBadge/ClassificationBadge";
import * as S from "@/components/shared/roomCard/RoomCard.style";
import RoomCardModal from "@/components/shared/roomCardModal/RoomCardModal";
import { RoomInfo } from "@/@types/roomInfo";
import { MAX_KEYWORDS } from "@/constants/room";
import { theme } from "@/styles/theme";
import { convertedDayToKorean } from "@/utils/convertToKorean";
import { convertDateToKorean, formatDday, formatLeftTime } from "@/utils/dateFormatter";

const DisplayLeftTime = (roomInfo: RoomInfo) => {
  if (roomInfo.roomStatus === "OPEN") {
    const dDay = formatDday(roomInfo.recruitmentDeadline);
    const leftTime = formatLeftTime(roomInfo.recruitmentDeadline);

    return (
      <>
        {dDay !== "종료됨" && "모집 마감"}
        <S.StyledDday aria-hidden>{dDay === "D-Day" ? leftTime : dDay}</S.StyledDday>
        <S.ScreenReader>
          {dDay === "D-Day" ? convertDateToKorean(leftTime) : convertedDayToKorean(dDay)}
        </S.ScreenReader>
      </>
    );
  }

  if (roomInfo.roomStatus === "PROGRESS") {
    const dDay = formatDday(roomInfo.reviewDeadline);
    const leftTime = formatLeftTime(roomInfo.reviewDeadline);

    return (
      <>
        {dDay !== "종료됨" && "리뷰 마감"}
        <S.StyledDday aria-hidden>{dDay === "D-Day" ? leftTime : dDay}</S.StyledDday>
        <S.ScreenReader>
          {dDay === "D-Day" ? convertDateToKorean(leftTime) : convertedDayToKorean(dDay)}
        </S.ScreenReader>
      </>
    );
  }

  return <>종료됨</>;
};

const DisplayLabel = (roomInfo: RoomInfo) => {
  return (
    <>
      <Label type={roomInfo.roomStatus} />
      {(roomInfo.participationStatus === "MANAGER" ||
        roomInfo.participationStatus === "PARTICIPATED") && <Label type="PARTICIPATED" />}
      {roomInfo.roomStatus === "CLOSE" && roomInfo.message !== "" && <Label type="FAIL" />}
    </>
  );
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

      <S.RoomCardContainer
        onClick={
          roomInfo.roomStatus === "OPEN" && roomInfo.isPrivate ? handleNoticeModal : handleOpenModal
        }
      >
        <S.ClassificationBadgeWrapper>
          <ClassificationBadge text={roomInfo.classification} />
        </S.ClassificationBadgeWrapper>

        <S.RoomInfoThumbnail as={ImageWithFallback} src={roomInfo.thumbnailLink} alt="" />

        <S.RoomInformation>
          <S.RoomTitle>{roomInfo.title}</S.RoomTitle>

          <S.KeywordsContainer>
            <S.KeywordWrapper>
              {displayedKeywords.length === 0 ? (
                <S.NoKeywordText>지정된 키워드 없음</S.NoKeywordText>
              ) : (
                displayedKeywords.map((keyword) => (
                  <S.KeywordText key={keyword}>{`#${keyword}`}</S.KeywordText>
                ))
              )}
            </S.KeywordWrapper>
          </S.KeywordsContainer>

          <S.EtcContainer>
            <S.LabelWrapper>
              <S.ScreenReader>방 상태</S.ScreenReader>
              {DisplayLabel(roomInfo)}
            </S.LabelWrapper>
            <S.DeadLineText>{DisplayLeftTime(roomInfo)}</S.DeadLineText>
            <S.JoinMember
              aria-label={`미션 참여 인원 최대 ${roomInfo.limitedParticipants}명, 현재 ${roomInfo.reviewerCount + roomInfo.bothCount}명`}
            >
              <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
              {roomInfo.reviewerCount + roomInfo.bothCount} / {roomInfo.limitedParticipants}
            </S.JoinMember>
          </S.EtcContainer>
        </S.RoomInformation>
      </S.RoomCardContainer>
    </>
  );
});

export default RoomCard;
