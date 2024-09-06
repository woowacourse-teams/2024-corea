import { useNavigate } from "react-router-dom";
import useMutateParticipateIn from "@/hooks/mutations/useMutateParticipateIn";
import Button from "@/components/common/button/Button";
import { RoomInfo } from "@/@types/roomInfo";

interface RoomCardModalButtonProps {
  roomInfo: RoomInfo;
}

const RoomCardModalButton = ({ roomInfo }: RoomCardModalButtonProps) => {
  const navigate = useNavigate();
  const { postParticipateInMutation } = useMutateParticipateIn();

  const handleParticipateClick = () => {
    postParticipateInMutation.mutate(roomInfo.id);
    navigate(`/rooms/${roomInfo.id}`);
  };

  if (roomInfo.isParticipated) {
    return (
      <Button variant="disable" size="small" disabled>
        참여중
      </Button>
    );
  } else if (roomInfo.isClosed) {
    return (
      <Button variant="disable" size="small" disabled>
        모집 완료
      </Button>
    );
  }
  return (
    <Button variant="primary" size="small" onClick={handleParticipateClick}>
      참여하기
    </Button>
  );
};

export default RoomCardModalButton;
