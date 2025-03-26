import * as S from "@/components/shared/roomList/RoomList.style";
import { errorCharacterBase64 } from "@/assets/character/error-character-base64";

const RoomListError = ({ message }: { message: string }) => {
  return (
    <S.EmptyContainer>
      <S.Character
        src={navigator.onLine ? "/error-character.png" : errorCharacterBase64}
        alt={message}
      />
      <p>{message}</p>
    </S.EmptyContainer>
  );
};

export default RoomListError;
