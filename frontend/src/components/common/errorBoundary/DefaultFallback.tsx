import Button from "../button/Button";
import React from "react";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { errorCharacter } from "@/assets";
import MESSAGES from "@/constants/message";

interface DefaultFallbackProps {
  onRetry: () => void;
}

const DefaultFallback = ({ onRetry }: DefaultFallbackProps) => {
  return (
    <S.FallbackContainer>
      <S.ErrorMessage>{MESSAGES.ERROR.BOUNDARY_TOTAL}</S.ErrorMessage>
      <S.Character src={errorCharacter} alt="에러 발생" />
      <Button onClick={onRetry} size="medium">
        다시 시도하기
      </Button>
    </S.FallbackContainer>
  );
};

export default DefaultFallback;
