import React, { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/iconButton/IconButton.style";
import IconKind from "@/@types/icon";

interface IconButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  iconKind: IconKind;
  text?: string;
}

const IconButton = ({ iconKind, text = "", ...rest }: IconButtonProps) => {
  return (
    <S.IconButtonContainer>
      <S.IconButtonBox aria-label="Icon Button" {...rest}>
        <S.StyledIcon kind={iconKind} />
      </S.IconButtonBox>
      {text !== "" && <S.IconButtonText>{text}</S.IconButtonText>}
    </S.IconButtonContainer>
  );
};

export default IconButton;
