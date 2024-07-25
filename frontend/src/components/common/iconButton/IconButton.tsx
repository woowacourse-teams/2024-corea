import React, { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/iconButton/IconButton.style";

interface IconButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  text: string;
  isSelected?: boolean;
}

const IconButton = ({ children, text, isSelected = false, ...rest }: IconButtonProps) => {
  return (
    <S.IconButtonContainer>
      <S.IconButtonBox aria-label="Icon Button" isSelected={isSelected} {...rest}>
        {children}
      </S.IconButtonBox>
      <S.IconButtonText>{text}</S.IconButtonText>
    </S.IconButtonContainer>
  );
};

export default IconButton;
