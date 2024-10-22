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
    <S.IconRadioButtonLabel>
      <S.HiddenRadioInput
        type="radio"
        name={name}
        checked={isSelected}
        onChange={handleChange}
        required
        {...rest}
        tabIndex={-1}
      />
      <S.IconRadioButtonBox $color={color} $isSelected={isSelected} aria-hidden={true} {...rest}>
        {children}
      </S.IconRadioButtonBox>
      <S.IconRadioButtonText aria-hidden={true}>{text}</S.IconRadioButtonText>
    </S.IconRadioButtonLabel>
  );
};

export default IconRadioButton;
