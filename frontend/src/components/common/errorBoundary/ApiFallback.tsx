import Button from "../button/Button";
import React from "react";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { errorCharacterBase64 } from "@/assets/character/error-charactor-base64";
import MESSAGES from "@/constants/message";

interface ApiFallbackProps {
  onRetry: () => void;
}

const ApiFallback = ({ onRetry }: ApiFallbackProps) => {
  return (
    <S.FallbackContainer>
      <S.Character
        src={navigator.onLine ? "/error-character.png" : errorCharacterBase64}
        alt="에러 발생"
      />
      <S.ErrorMessage>{MESSAGES.ERROR.BOUNDARY_API}</S.ErrorMessage>
      <Button onClick={onRetry} size="medium">
        다시 시도하기
      </Button>
    </S.FallbackContainer>
  );
};

export default ApiFallback;
