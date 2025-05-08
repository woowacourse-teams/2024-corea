import StaticHeader from "../header/StaticHeader";
import React from "react";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { errorCharacterBase64 } from "@/assets/character/error-character-base64";

interface FallbackProps {
  message: string;
  buttonText: string;
  onButtonClick: () => void;
}

const Fallback = ({ message, buttonText, onButtonClick }: FallbackProps) => {
  return (
    <>
      <StaticHeader />
      <S.FallbackContainer>
        <S.Character
          src={navigator.onLine ? "/error-character.png" : errorCharacterBase64}
          alt={message}
        />
        <S.ErrorMessage>{message}</S.ErrorMessage>
        <Button onClick={onButtonClick} size="medium">
          {buttonText}
        </Button>
      </S.FallbackContainer>
    </>
  );
};

export default Fallback;
