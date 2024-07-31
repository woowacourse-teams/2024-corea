import React, { InputHTMLAttributes } from "react";
import * as S from "@/components/common/iconRadioButton/IconRadioButton.style";

interface IconRadioButtonProps extends Omit<InputHTMLAttributes<HTMLInputElement>, "id"> {
  id: number;
  text: string;
}

const IconRadioButton = ({ children, id, text, ...rest }: IconRadioButtonProps) => {
  return (
    <S.IconRadioButtonContainer>
      <S.HiddenRadioInput type="radio" id={id.toString()} {...rest} />
      <S.IconRadioButtonBox aria-label="Icon Button" {...rest}>
        {children}
      </S.IconRadioButtonBox>
      <S.IconRadioButtonText>{text}</S.IconRadioButtonText>
    </S.IconRadioButtonContainer>
  );
};

export default IconRadioButton;
