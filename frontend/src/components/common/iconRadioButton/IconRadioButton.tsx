import { InputHTMLAttributes } from "react";
import * as S from "@/components/common/iconRadioButton/IconRadioButton.style";

interface IconRadioButtonProps extends Omit<InputHTMLAttributes<HTMLInputElement>, "onChange"> {
  text: string;
  name: string;
  value: number;
  isSelected?: boolean;
  color?: string;
  onChange: (id: number) => void;
}

const IconRadioButton = ({
  children,
  text,
  name,
  value,
  isSelected,
  color,
  onChange,
  ...rest
}: IconRadioButtonProps) => {
  const handleChange = () => {
    onChange(value);
  };

  return (
    <S.IconRadioButtonContainer>
      <S.HiddenRadioInput
        type="radio"
        name={name}
        checked={isSelected}
        onChange={handleChange}
        {...rest}
        tabIndex={-1}
      />
      <S.IconRadioButtonBox
        $color={color}
        $isSelected={isSelected}
        aria-label="Icon Button"
        {...rest}
      >
        {children}
      </S.IconRadioButtonBox>
      <S.IconRadioButtonText>{text}</S.IconRadioButtonText>
    </S.IconRadioButtonContainer>
  );
};

export default IconRadioButton;
