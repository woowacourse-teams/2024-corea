import { useNavigate } from "react-router-dom";
import useMutateParticipateIn from "@/hooks/mutations/useMutateParticipateIn";
import Button from "@/components/common/button/Button";
import { RoomInfo } from "@/@types/roomInfo";

interface RoomCardModalButtonProps {
  roomInfo: RoomInfo;
}

const RoomCardModalButton = ({ roomInfo }: RoomCardModalButtonProps) => {
  const { postParticipateInMutation } = useMutateParticipateIn();
  const navigate = useNavigate();

  const participateRoom = () => {
    postParticipateInMutation.mutate(roomInfo.id, {
      onSuccess: () => navigate(`/rooms/${roomInfo.id}`),
    });
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
    <Button variant="primary" size="small" onClick={participateRoom}>
      참여하기
    </Button>
  );
};

export default RoomCardModalButton;
