import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import Button from "@/components/common/button/Button";
import Checkbox from "@/components/common/checkbox/Checkbox";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import * as S from "@/components/shared/roomCardModal/RoomCardModal.style";
import { Role, RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";

interface RoomCardModalButtonProps {
  roomInfo: RoomInfo;
}

const MAX_MATCHING_SIZE = 5;

const RoomCardModalButton = ({ roomInfo }: RoomCardModalButtonProps) => {
  const navigate = useNavigate();

  const [matchingSize, setMatchingSize] = useState(roomInfo.matchingSize);
  const [isNoticeModalOpen, setIsNoticeModalOpen] = useState(false);
  const { postParticipateInMutation } = useMutateRoom();
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const isReviewer = localStorage.getItem("memberRole") === "REVIEWER";
  const userRole = isReviewer ? "REVIEWER" : "BOTH";

  const handleNoticeModal = () => {
    setIsNoticeModalOpen((prev) => !prev);
  };

  const handleParticipateRoomClick = () => {
    postParticipateInMutation.mutate(
      {
        roomId: roomInfo.id,
        role: userRole as Role,
        matchingSize,
      },
      {
        onSuccess: () => navigate(`/rooms/${roomInfo.id}`),
      },
    );
  };

  if (!isLoggedIn) {
    return (
      <Button variant="disable" size="medium" disabled>
        로그인 후 참여 가능
      </Button>
    );
  }

  if (roomInfo.participationStatus !== "NOT_PARTICIPATED") {
    return (
      <Button variant="disable" size="small" disabled>
        참여중
      </Button>
    );
  }

  if (roomInfo.roomStatus !== "OPEN") {
    return (
      <Button variant="disable" size="small" disabled>
        모집 완료
      </Button>
    );
  }

  return (
    <S.ButtonContainer>
      <ConfirmModal
        isOpen={isNoticeModalOpen}
        onClose={handleNoticeModal}
        onConfirm={handleParticipateRoomClick}
        onCancel={handleNoticeModal}
      >
        해당 링크를 참고하여 본인 레포에 PR을 남겨주세요 <br /> (본인 레포에 PR을 작성하지 않으면 방
        매칭이 안돼요 😥)
        <HoverStyledLink
          to={"https://github.com/2024-Code-Review-Area/description-convenience-store-7"}
          target="_blank"
          rel="noreferrer"
        >
          🔗 방 참여방법 보러가기
        </HoverStyledLink>
      </ConfirmModal>
      <S.FormContainer>
        <S.FormWrapper onClick={(e) => e.stopPropagation()}>
          <S.MatchingSizeContainer>
            <p>
              {userRole === "REVIEWER"
                ? "선호하는 리뷰이 인원 선택:"
                : "선호하는 상호 리뷰 인원 선택:"}
            </p>
            <Button
              variant={matchingSize === roomInfo.matchingSize ? "disable" : "primary"}
              size="xSmall"
              disabled={matchingSize === roomInfo.matchingSize}
              onClick={(event) => {
                setMatchingSize(Math.max(matchingSize - 1, roomInfo.matchingSize));
                event.stopPropagation();
              }}
            >
              -
            </Button>
            <S.ReviewCount>{matchingSize}</S.ReviewCount>
            <Button
              variant={matchingSize === MAX_MATCHING_SIZE ? "disable" : "primary"}
              size="xSmall"
              disabled={matchingSize === MAX_MATCHING_SIZE}
              onClick={(event) => {
                setMatchingSize(Math.min(matchingSize + 1, MAX_MATCHING_SIZE));
                event.stopPropagation();
              }}
            >
              +
            </Button>
          </S.MatchingSizeContainer>

          {isReviewer && (
            <Checkbox
              id="reviewer-checkbox"
              label="리뷰어로만 참여하기"
              checked={true}
              readonly={true}
            />
          )}
        </S.FormWrapper>
      </S.FormContainer>

      <Button variant="primary" size="small" onClick={handleNoticeModal}>
        참여하기
      </Button>
    </S.ButtonContainer>
  );
};

export default RoomCardModalButton;
