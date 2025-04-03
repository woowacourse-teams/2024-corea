import Button from "@/components/common/button/Button";
import * as S from "@/components/shared/roomList/RoomList.style";
import { errorCharacterBase64 } from "@/assets/character/error-character-base64";

const RoomListError = ({ message, onRetry }: { message: string; onRetry: () => void }) => {
  return (
    <S.EmptyContainer>
      <S.Character
        src={navigator.onLine ? "/error-character.png" : errorCharacterBase64}
        alt={message}
      />
      <p>{message}</p>
      <Button onClick={onRetry} size="small">
        다시 시도하기
      </Button>
    </S.EmptyContainer>
  );
};

export default RoomListError;
