import RoomCardModalButton from "./RoomCardModalButton";
import Icon from "@/components/common/icon/Icon";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import * as S from "@/components/shared/roomCardModal/RoomCardModal.style";
import { RoomInfo } from "@/@types/roomInfo";
import { profile } from "@/assets";
import { formatDateTimeString } from "@/utils/dateFormatter";

interface RoomCardModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: RoomInfo;
}

const RoomCardModal = ({ isOpen, onClose, roomInfo }: RoomCardModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <S.RoomCardModalContainer>
        <S.RoomInfoThumbnail src={roomInfo.thumbnailLink} alt={roomInfo.title} />

        <S.MainContainer>
          <S.ManagerContainer>
            <S.ProfileContainer>
              <img src={profile} alt="프로필 사진" />
              <span> {roomInfo.manager}</span>
            </S.ProfileContainer>
            <div>
              {roomInfo.isClosed ? (
                <Label type="close" text="마감됨" />
              ) : (
                <Label type="open" text="모집중" />
              )}
            </div>
          </S.ManagerContainer>

          <S.TitleContainer>
            <S.RoomTitle>{roomInfo.title}</S.RoomTitle>
            <S.RepositoryLink href={roomInfo.repositoryLink}>
              <Icon kind="link" />
              저장소 바로가기
            </S.RepositoryLink>
          </S.TitleContainer>
        </S.MainContainer>

        <S.EtcContainer>
          <S.InfoRow>
            <S.InfoTitle>모집 마감일</S.InfoTitle>
            <S.InfoContent>{formatDateTimeString(roomInfo.recruitmentDeadline)}</S.InfoContent>
          </S.InfoRow>
          <S.InfoRow>
            <S.InfoTitle>리뷰 마감일</S.InfoTitle>
            <S.InfoContent>{formatDateTimeString(roomInfo.reviewDeadline)}</S.InfoContent>
          </S.InfoRow>
          <S.InfoRow>
            <S.InfoTitle>상호 리뷰 인원</S.InfoTitle>
            <S.InfoContent>{roomInfo.matchingSize}명</S.InfoContent>
          </S.InfoRow>
          <S.InfoRow>
            <S.InfoTitle>미션 참여 인원</S.InfoTitle>
            <S.InfoContent>
              {roomInfo.currentParticipants} / {roomInfo.limitedParticipants}
            </S.InfoContent>
          </S.InfoRow>
        </S.EtcContainer>

        <S.DescriptionContainer>
          <S.KeywordsContainer>
            <S.KeywordWrapper>
              {roomInfo.keywords.map((keyword) => (
                <S.KeywordText key={keyword}>#{keyword}</S.KeywordText>
              ))}
            </S.KeywordWrapper>
          </S.KeywordsContainer>
          <S.ContentContainer>{roomInfo.content}</S.ContentContainer>
        </S.DescriptionContainer>

        <S.ButtonContainer>
          <RoomCardModalButton roomInfo={roomInfo} />
        </S.ButtonContainer>
      </S.RoomCardModalContainer>
    </Modal>
  );
};

export default RoomCardModal;
