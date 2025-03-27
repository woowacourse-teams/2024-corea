import * as S from "@/components/shared/roomList/RoomList.style";
import { defaultCharacter } from "@/assets";

const RoomListEmpty = ({ message }: { message: string }) => {
  return (
    <S.EmptyContainer>
      <S.Character src={defaultCharacter} alt="기본 캐릭터" />
      <p>{message}</p>
    </S.EmptyContainer>
  );
};

export default RoomListEmpty;
