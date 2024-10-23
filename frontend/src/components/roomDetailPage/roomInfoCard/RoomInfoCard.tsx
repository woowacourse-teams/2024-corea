import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import * as S from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard.style";
import ClassificationBadge from "@/components/shared/classificationBadge/ClassificationBadge";
import { RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";
import { theme } from "@/styles/theme";
import { displayLeftTime, formatDateTimeString, formatDday } from "@/utils/dateFormatter";

const RoomInfoCard = ({ roomInfo }: { roomInfo: RoomInfo }) => {
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
            {roomInfo.keywords.length === 1 ? (
              <S.NoKeywordText>지정된 키워드 없음</S.NoKeywordText>
            ) : (
              roomInfo.keywords.map((keyword) => (
                <Label
                  key={keyword}
                  type="KEYWORD"
                  text={keyword}
                  size="small"
                  backgroundColor={theme.COLOR.primary1}
                />
              ))
            )}
          </S.RoomTagBox>
          <S.RoomContentSmall>{roomInfo.content}</S.RoomContentSmall>
        </S.RoomContentBox>

        <S.RoomContentBox>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
            <span>방 생성자 : </span>
            <span id="githubid"> {roomInfo.manager}</span>
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
            <span>총 인원 : </span>
            {roomInfo.reviewerCount + roomInfo.bothCount} / {roomInfo.limitedParticipants}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
            <span>리뷰어 인원 : </span>
            {roomInfo.reviewerCount} / {roomInfo.reviewerCount + roomInfo.bothCount}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
            <span>참여자 인원 : </span>
            {roomInfo.bothCount} / {roomInfo.reviewerCount + roomInfo.bothCount}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.8rem" color={theme.COLOR.grey4} />
            <span>최소 상호 리뷰 인원 : </span>
            {roomInfo.matchingSize}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <S.ContentLineBreak>
              <S.RoomContentSmall>
                <Icon kind="calendar" size="1.8rem" color={theme.COLOR.grey4} />
                <span>모집 마감일: </span>
              </S.RoomContentSmall>
              <div>
                <S.DateTimeText>
                  {formatDateTimeString(roomInfo.recruitmentDeadline)}
                </S.DateTimeText>
                <S.StyledDday data-testid="recruitLeftTime">
                  {roomInfo.roomStatus === "OPEN" &&
                    formatDday(roomInfo.recruitmentDeadline) !== "종료됨" &&
                    displayLeftTime(roomInfo.recruitmentDeadline)}
                </S.StyledDday>
              </div>
            </S.ContentLineBreak>
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <S.ContentLineBreak>
              <S.RoomContentSmall>
                <Icon kind="calendar" size="1.8rem" color={theme.COLOR.grey4} />
                <span>리뷰 및 피드백 마감일: </span>
              </S.RoomContentSmall>
              <div>
                <S.DateTimeText>{formatDateTimeString(roomInfo.reviewDeadline)}</S.DateTimeText>
                <S.StyledDday data-testid="reviewLeftTime">
                  {(roomInfo.roomStatus === "OPEN" || roomInfo.roomStatus === "PROGRESS") &&
                    formatDday(roomInfo.reviewDeadline) !== "종료됨" &&
                    displayLeftTime(roomInfo.reviewDeadline)}
                </S.StyledDday>
              </div>
            </S.ContentLineBreak>
          </S.RoomContentSmall>
        </S.RoomContentBox>
      </S.RoomInfoCardContent>
    </S.RoomInfoCardContainer>
  );
};

export default RoomInfoCard;
