import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import * as S from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard.style";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";
import { displayLeftTime, formatDateTimeString, formatDday } from "@/utils/dateFormatter";

const RoomInfoCard = ({ roomInfo }: { roomInfo: RoomInfo }) => {
  return (
    <S.RoomInfoCardContainer>
      <S.RoomInfoCardImg
        as={ImageWithFallback}
        src={roomInfo.thumbnailLink}
        alt="방 썸네일 이미지"
      />
      <S.RoomInfoCardContent>
        <S.RoomHeaderWrapper>
          <S.RoomTitle>{roomInfo.title}</S.RoomTitle>
          <S.RepositoryLink href={roomInfo.repositoryLink} target="_blank" rel="noreferrer">
            <Icon kind="link" />
            저장소 바로가기
          </S.RepositoryLink>
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
            <Icon kind="person" size="1.4rem" color={theme.COLOR.grey4} />
            <span>방 생성자 : </span>
            <span id="githubid"> {roomInfo.manager}</span>
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.4rem" color={theme.COLOR.grey4} />
            <span>현재 참여 인원 : </span>
            {roomInfo.currentParticipants} / {roomInfo.limitedParticipants}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <Icon kind="person" size="1.4rem" color={theme.COLOR.grey4} />
            <span>최소 상호 리뷰 인원 : </span>
            {roomInfo.matchingSize}명
          </S.RoomContentSmall>
          <S.RoomContentSmall>
            <S.ContentLineBreak>
              <S.RoomContentSmall>
                <Icon kind="calendar" size="1.4rem" color={theme.COLOR.grey4} />
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
                <Icon kind="calendar" size="1.4rem" color={theme.COLOR.grey4} />
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
