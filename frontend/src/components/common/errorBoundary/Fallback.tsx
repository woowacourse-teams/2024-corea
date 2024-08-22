import { useLocation } from "react-router-dom";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { BannerContainer } from "@/components/main/banner/Banner.style";
import ErrorCharacter from "@/assets/error-character.png";

interface FallbackProps {
  message: string;
  resetError: () => void;
}

const Fallback = ({ message, resetError }: FallbackProps) => {
  const { pathname } = useLocation();
  const isMain = pathname === "/";

  return (
    <>
      {isMain && <BannerContainer />}
      <S.FallbackContainer>
        <S.ErrorMessage>{message}</S.ErrorMessage>
        <S.Character src={ErrorCharacter} alt="로그인 중" />
        <Button onClick={resetError} size="medium">
          다시 시도하기
        </Button>
      </S.FallbackContainer>
    </>
  );
};

export default Fallback;
