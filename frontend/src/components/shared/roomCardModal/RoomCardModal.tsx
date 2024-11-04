import RoomCardModalButton from "./RoomCardModalButton";
import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import ClassificationBadge from "@/components/shared/classificationBadge/ClassificationBadge";
import * as S from "@/components/shared/roomCardModal/RoomCardModal.style";
import { RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";
import { convertDateToKorean, formatDateTimeString } from "@/utils/dateFormatter";

interface RoomCardModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: RoomInfo;
}

const RoomCardModal = ({ isOpen, onClose, roomInfo }: RoomCardModalProps) => {
  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <S.RoomCardModalContainer>
        <S.HeaderContainer>
          <S.ClassificationBadgeWrapper>
            <ClassificationBadge text={roomInfo.classification} />
          </S.ClassificationBadgeWrapper>

          <S.RoomInfoThumbnail as={ImageWithFallback} src={roomInfo.thumbnailLink} alt="" />

          <S.MainContainer>
            <S.ManagerContainer>
              <S.ProfileContainer>
                <S.IconWrapper>
                  <Icon kind="person" />
                </S.IconWrapper>
                <span aria-hidden>{roomInfo.manager}</span>
                <S.ScreenReader>{`방 생성자 ${roomInfo.manager}`}</S.ScreenReader>
              </S.ProfileContainer>
              <Label type={roomInfo.roomStatus} />
            </S.ManagerContainer>

            <S.TitleContainer>
              <S.RoomTitle>{roomInfo.title}</S.RoomTitle>
              <HoverStyledLink to={roomInfo.repositoryLink} target="_blank" rel="noreferrer">
                <S.RepositoryLink>
                  <Icon kind="link" size="1.8rem" />
                  저장소 바로가기
                </S.RepositoryLink>
              </HoverStyledLink>
            </S.TitleContainer>
          </S.MainContainer>
        </S.HeaderContainer>

        <S.EtcContainer>
          <S.InfoRow>
            <S.InfoTitle id="recruitDeadlineTitle" aria-hidden>
              모집 마감일
            </S.InfoTitle>
            <S.InfoContent aria-hidden>
              {formatDateTimeString(roomInfo.recruitmentDeadline)}
            </S.InfoContent>
            <S.ScreenReader>
              {`모집 마감일 ${convertDateToKorean(formatDateTimeString(roomInfo.recruitmentDeadline))}`}
            </S.ScreenReader>
          </S.InfoRow>

          <S.InfoRow>
            <S.InfoTitle id="reviewDeadlineTitle" aria-hidden>
              리뷰 마감일
            </S.InfoTitle>
            <S.InfoContent aria-hidden>
              {formatDateTimeString(roomInfo.reviewDeadline)}
            </S.InfoContent>
            <S.ScreenReader>
              {`리뷰 마감일 ${convertDateToKorean(formatDateTimeString(roomInfo.reviewDeadline))}`}
            </S.ScreenReader>
          </S.InfoRow>

          <S.InfoRow>
            <S.InfoTitle aria-hidden>최소 상호 리뷰 인원</S.InfoTitle>
            <S.InfoContent aria-hidden>{roomInfo.matchingSize}명</S.InfoContent>
            <S.ScreenReader>{`최소 상호 리뷰 인원 ${roomInfo.matchingSize}명`}</S.ScreenReader>
          </S.InfoRow>

          <S.InfoRow>
            <S.InfoTitle aria-hidden>총 인원</S.InfoTitle>
            <S.InfoContent aria-hidden>
              {roomInfo.reviewerCount + roomInfo.bothCount} / {roomInfo.limitedParticipants}명
              <span id="sub">
                리뷰어 {roomInfo.reviewerCount}, 참여자 {roomInfo.bothCount}
              </span>
            </S.InfoContent>
            <S.ScreenReader>{`미션 참여 인원 최대 ${roomInfo.limitedParticipants}명, 현재 ${roomInfo.reviewerCount + roomInfo.bothCount}명 `}</S.ScreenReader>
          </S.InfoRow>
        </S.EtcContainer>

        <S.DescriptionContainer>
          <S.KeywordsContainer>
            <S.KeywordWrapper>
              {displayedKeywords.length === 0 ? (
                <S.NoKeywordText aria-hidden>지정된 키워드 없음</S.NoKeywordText>
              ) : (
                <S.KeywordText>
                  {displayedKeywords.map((keyword) => `#${keyword}`).join(" ")}
                </S.KeywordText>
              )}
            </S.KeywordWrapper>
          </S.KeywordsContainer>
          <S.ContentContainer>{roomInfo.content}</S.ContentContainer>
        </S.DescriptionContainer>

        <S.ButtonWRapper>
          <RoomCardModalButton roomInfo={roomInfo} />
        </S.ButtonWRapper>
      </S.RoomCardModalContainer>
    </Modal>
  );
};

export default RoomCardModal;
