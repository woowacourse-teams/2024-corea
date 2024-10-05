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

const RoomCardModalButton = ({ roomInfo }: RoomCardModalButtonProps) => {
  const navigate = useNavigate();
  const [userRole, setUserRole] = useState<Role>("BOTH");
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
      <Checkbox
        id="reviewer-checkbox"
        label="리뷰어로 참여하기"
        checked={userRole === "REVIEWER"}
        onChange={handleRoleChange}
      />
      <Button variant="primary" size="small" onClick={handleParticipateRoomClick}>
        참여하기
      </Button>
    </S.ButtonContainer>
  );
};

export default RoomCardModalButton;
