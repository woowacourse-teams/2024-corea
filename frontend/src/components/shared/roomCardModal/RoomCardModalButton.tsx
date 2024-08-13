import useMutateParticipateIn from "@/hooks/mutations/useMutateParticipateIn";
import Button from "@/components/common/button/Button";
import { RoomInfo } from "@/@types/roomInfo";

interface RoomCardModalButtonProps {
  roomInfo: RoomInfo;
}

const RoomCardModalButton = ({ roomInfo }: RoomCardModalButtonProps) => {
  const { postParticipateInMutation } = useMutateParticipateIn();

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
    <Button
      variant="primary"
      size="small"
      onClick={() => postParticipateInMutation.mutate(roomInfo.id)}
    >
      참여하기
    </Button>
  );
};

export default RoomCardModalButton;
