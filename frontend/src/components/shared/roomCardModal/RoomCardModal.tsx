import RoomCardModalButton from "./RoomCardModalButton";
import Icon from "@/components/common/icon/Icon";
import ImageWithFallback from "@/components/common/img/ImageWithFallback";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import * as S from "@/components/shared/roomCardModal/RoomCardModal.style";
import { RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";
import { formatDateTimeString } from "@/utils/dateFormatter";

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
          <S.RoomInfoThumbnail
            as={ImageWithFallback}
            src={roomInfo.thumbnailLink}
            alt={roomInfo.title}
          />
          <S.MainContainer>
            <S.ManagerContainer>
              <S.ProfileContainer>
                <S.IconWrapper>
                  <Icon kind="person" />
                </S.IconWrapper>
                <span> {roomInfo.manager}</span>
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
            <S.InfoTitle>모집 마감일</S.InfoTitle>
            <S.InfoContent>{formatDateTimeString(roomInfo.recruitmentDeadline)}</S.InfoContent>
          </S.InfoRow>
          <S.InfoRow>
            <S.InfoTitle>리뷰 및 피드백 마감일</S.InfoTitle>
            <S.InfoContent>{formatDateTimeString(roomInfo.reviewDeadline)}</S.InfoContent>
          </S.InfoRow>
          <S.InfoRow>
            <S.InfoTitle>최소 상호 리뷰 인원</S.InfoTitle>
            <S.InfoContent>{roomInfo.matchingSize}명</S.InfoContent>
          </S.InfoRow>
          <S.InfoRow>
            <S.InfoTitle>미션 참여 인원</S.InfoTitle>
            <S.InfoContent>
              {roomInfo.currentParticipants}명 / {roomInfo.limitedParticipants}명
            </S.InfoContent>
          </S.InfoRow>
        </S.EtcContainer>
        <S.DescriptionContainer>
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
