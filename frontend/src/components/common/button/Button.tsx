import { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/button/Button.style";

export type ButtonVariant = "primary" | "secondary" | "disable" | "confirm" | "error";
export type ButtonSize = "xSmall" | "small" | "medium" | "large";

export interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
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
      tabIndex={disabled ? -1 : 0}
      {...rest}
    >
      {children}
    </S.ButtonContainer>
  );
};

export default Button;
