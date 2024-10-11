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
  const [userRole, setUserRole] = useState<Role>("BOTH");
  const [matchingSize, setMatchingSize] = useState(roomInfo.matchingSize);
  const { postParticipateInMutation } = useMutateRoom();
  const isLoggedIn = !!localStorage.getItem("accessToken");

  const handleParticipateRoomClick = () => {
    postParticipateInMutation.mutate(
      {
        roomId: roomInfo.id,
        role: userRole as Role,
      },
      {
        onSuccess: () => navigate(`/rooms/${roomInfo.id}`),
      },
    );
  };

  const handleRoleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    event.target.checked ? setUserRole("REVIEWER") : setUserRole("BOTH");
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
      <S.MatchingSizeContainer>
        <p>상호 리뷰 인원 선택</p>
        <Button
          variant={matchingSize === roomInfo.matchingSize ? "disable" : "primary"}
          size="xSmall"
          disabled={matchingSize === roomInfo.matchingSize}
          onClick={() => setMatchingSize(Math.max(matchingSize - 1, roomInfo.matchingSize))}
        >
          -
        </Button>
        <span>{matchingSize}</span>
        <Button
          variant={matchingSize === MAX_MATCHING_SIZE ? "disable" : "primary"}
          size="xSmall"
          disabled={matchingSize === MAX_MATCHING_SIZE}
          onClick={() => setMatchingSize(Math.min(matchingSize + 1, MAX_MATCHING_SIZE))}
        >
          +
        </Button>
      </S.MatchingSizeContainer>

      <Button variant="primary" size="small" onClick={handleParticipateRoomClick}>
        참여하기
      </Button>

      <Checkbox
        id="reviewer-checkbox"
        label="리뷰어로만 참여하기"
        checked={userRole === "REVIEWER"}
        onChange={handleRoleChange}
      />
    </S.ButtonContainer>
  );
};

export default RoomCardModalButton;
