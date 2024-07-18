import React from "react";
import * as S from "@/components/common/iconButton/IconButton.style";
import IconKind from "@/@types/icon";

interface IconButtonProps {
  iconKind: IconKind;
  text?: string;
}

const IconButton = ({ iconKind, text = "" }: IconButtonProps) => {
  return (
    <S.IconButtonContainer>
      <S.IconButtonImg>
        <S.StyledIcon kind={iconKind} />
      </S.IconButtonImg>
      {text !== "" && <S.IconButtonText>{text}</S.IconButtonText>}
    </S.IconButtonContainer>
  );
};

export default IconButton;
