import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import Button from "@/components/common/button/Button";
import Checkbox from "@/components/common/checkbox/Checkbox";
import * as S from "@/components/shared/roomCardModal/RoomCardModal.style";
import { Role, RoomInfo } from "@/@types/roomInfo";

interface RoomCardModalButtonProps {
  roomInfo: RoomInfo;
}

const MAX_MATCHING_SIZE = 5;

const RoomCardModalButton = ({ roomInfo }: RoomCardModalButtonProps) => {
  const navigate = useNavigate();

  const [matchingSize, setMatchingSize] = useState(roomInfo.matchingSize);
  const { postParticipateInMutation } = useMutateRoom();
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const isReviewer = localStorage.getItem("memberRole") === "REVIEWER";
  const userRole = isReviewer ? "REVIEWER" : "BOTH";

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
            <span>{matchingSize}</span>
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

      <Button variant="primary" size="small" onClick={handleParticipateRoomClick}>
        참여하기
      </Button>
    </S.ButtonContainer>
  );
};

export default RoomCardModalButton;
