import { useNavigate } from "react-router-dom";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { errorCharacterBase64 } from "@/assets/character/error-character-base64";

const NotFoundPage = () => {
  const navigate = useNavigate();

  return (
    <S.FallbackContainer>
      <S.Character
        src={navigator.onLine ? "/error-character.png" : errorCharacterBase64}
        alt="해당 페이지를 찾을 수 없습니다."
      />
      <S.ErrorMessage>해당 페이지를 찾을 수 없습니다.</S.ErrorMessage>
      <Button onClick={() => navigate("/")} size="medium">
        홈 화면으로 가기
      </Button>
    </S.FallbackContainer>
  );
};

export default NotFoundPage;
