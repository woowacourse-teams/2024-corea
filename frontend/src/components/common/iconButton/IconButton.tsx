import React, { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/iconButton/IconButton.style";
import IconKind from "@/@types/icon";

interface IconButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text: string;
}

const IconButton = ({ children, text, ...rest }: IconButtonProps) => {
  return (
    <S.IconButtonContainer>
      <S.IconButtonBox aria-label="Icon Button" {...rest}>
        {children}
      </S.IconButtonBox>
      <S.IconButtonText>{text}</S.IconButtonText>
    </S.IconButtonContainer>
  );
};

export default IconButton;
