import { useLocation } from "react-router-dom";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { BannerContainer } from "@/components/main/banner/Banner.style";
import { errorCharacter } from "@/assets";

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
        <S.Character src={errorCharacter} alt="에러 발생" />
        <Button onClick={resetError} size="medium">
          다시 시도하기
        </Button>
      </S.FallbackContainer>
    </>
  );
};

export default Fallback;
