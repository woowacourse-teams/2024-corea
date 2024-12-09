import React from "react";
import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import * as S from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard.style";
import ClassificationBadge from "@/components/shared/classificationBadge/ClassificationBadge";
import { RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";
import { theme } from "@/styles/theme";
import {
  convertDateToKorean,
  displayLeftTime,
  formatDateTimeString,
  formatDday,
} from "@/utils/dateFormatter";

const RoomInfoCard = ({ roomInfo }: { roomInfo: RoomInfo }) => {
  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  return (
    <S.RoomInfoCardContainer>
      <S.ClassificationBadgeWrapper>
        <ClassificationBadge text={roomInfo.classification} />
      </S.ClassificationBadgeWrapper>

      <S.RoomInfoCardImg
        as={ImageWithFallback}
        src={roomInfo.thumbnailLink}
        alt="방 썸네일 이미지"
      />
      <S.RoomInfoCardContent>
        <S.RoomHeaderWrapper>
          <S.RoomTitle>{roomInfo.title}</S.RoomTitle>

          <HoverStyledLink to={roomInfo.repositoryLink} target="_blank" rel="noreferrer">
            <S.RepositoryLink>
              <Icon kind="link" size="2.4rem" />
              저장소 바로가기
            </S.RepositoryLink>
          </HoverStyledLink>
        </S.RoomHeaderWrapper>

        <S.RoomContentBox>
          <S.RoomTagBox>
            <Label type="MANAGER" managerText={roomInfo.manager} size="semiSmall_bold" />
            {displayedKeywords.length === 0 ? (
              <S.NoKeywordText>지정된 키워드 없음</S.NoKeywordText>
            ) : (
              displayedKeywords.map((keyword) => (
                <Label
                  key={keyword}
                  type="KEYWORD"
                  text={keyword}
                  size="semiSmall_bold"
                  backgroundColor={theme.COLOR.primary1}
                />
              ))
            )}
          </S.RoomTagBox>
          <S.RoomContentSmall>{roomInfo.content}</S.RoomContentSmall>
        </S.RoomContentBox>

        <S.RoomContentBox>
          <S.RoomContentSmall>
            <S.ContentLineBreak aria-hidden>
              <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
              <S.ContentTerm>참여 인원: </S.ContentTerm>
              <S.ContentDescription>
                {roomInfo.bothCount} / {roomInfo.limitedParticipants}명
              </S.ContentDescription>
              <S.ContentTermSub>
                리뷰어 {roomInfo.reviewerCount}, 참여자 {roomInfo.bothCount}
              </S.ContentTermSub>
            </S.ContentLineBreak>
            <S.ScreenReader>{`총 인원 : ${roomInfo.limitedParticipants}명 중 ${roomInfo.bothCount}명, 리뷰어 ${roomInfo.reviewerCount}명, 참여자 ${roomInfo.bothCount}명`}</S.ScreenReader>
          </S.RoomContentSmall>

          <S.RoomContentSmall>
            <S.ContentLineBreak aria-hidden>
              <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
              <S.ContentTerm>최소 상호 리뷰 인원: </S.ContentTerm>
              <S.ContentDescription>{roomInfo.matchingSize}명</S.ContentDescription>
            </S.ContentLineBreak>
            <S.ScreenReader>{`최소 상호 리뷰 인원 ${roomInfo.matchingSize}명`}</S.ScreenReader>
          </S.RoomContentSmall>

          <S.RoomContentSmall>
            <S.ContentLineBreak aria-hidden>
              <Icon kind="calendar" size="1.8rem" color={theme.COLOR.grey4} />
              <S.ContentTerm>모집 마감일: </S.ContentTerm>
              <S.ContentDescription>
                <S.DateTimeText>
                  {formatDateTimeString(roomInfo.recruitmentDeadline)}
                </S.DateTimeText>
                <S.StyledDday data-testid="recruitLeftTime">
                  {roomInfo.roomStatus === "OPEN" &&
                    formatDday(roomInfo.recruitmentDeadline) !== "종료됨" &&
                    displayLeftTime(roomInfo.recruitmentDeadline)}
                </S.StyledDday>
              </S.ContentDescription>
            </S.ContentLineBreak>
            <S.ScreenReader>
              {`모집 마감일 ${convertDateToKorean(formatDateTimeString(roomInfo.recruitmentDeadline))}`}
            </S.ScreenReader>
          </S.RoomContentSmall>

          <S.RoomContentSmall>
            <S.ContentLineBreak aria-hidden>
              <Icon kind="calendar" size="1.8rem" color={theme.COLOR.grey4} />
              <S.ContentTerm>리뷰 마감일: </S.ContentTerm>
              <S.ContentDescription>
                <S.DateTimeText>{formatDateTimeString(roomInfo.reviewDeadline)}</S.DateTimeText>
                <S.StyledDday data-testid="reviewLeftTime">
                  {(roomInfo.roomStatus === "OPEN" || roomInfo.roomStatus === "PROGRESS") &&
                    formatDday(roomInfo.reviewDeadline) !== "종료됨" &&
                    displayLeftTime(roomInfo.reviewDeadline)}
                </S.StyledDday>
              </S.ContentDescription>
            </S.ContentLineBreak>
            <S.ScreenReader>
              {`리뷰 마감일 ${convertDateToKorean(formatDateTimeString(roomInfo.reviewDeadline))}`}
            </S.ScreenReader>
          </S.RoomContentSmall>
        </S.RoomContentBox>
      </S.RoomInfoCardContent>
    </S.RoomInfoCardContainer>
  );
};

export default RoomInfoCard;
