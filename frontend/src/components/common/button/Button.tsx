import { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/button/Button.style";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "disable" | "lightBlue" | "error";
  size?: "xSmall" | "small" | "medium" | "large";
}

const Button = ({
  children,
  variant = "primary",
  size = "medium",
  disabled = false,
  ...rest
}: ButtonProps) => {
  return (
    <S.ButtonContainer
      $variant={disabled ? "disable" : variant}
      $size={size}
      disabled={disabled}
      {...rest}
    >
      {children}
    </S.ButtonContainer>
  );
};

export default Button;
