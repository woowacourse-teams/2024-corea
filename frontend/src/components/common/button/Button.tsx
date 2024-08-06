import React from "react";
import { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/button/Button.style";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "disable";
  size?: "small" | "medium" | "large";
}

const Button = ({ children, variant = "primary", size = "medium", ...rest }: ButtonProps) => {
  return (
    <S.ButtonContainer $variant={variant} $size={size} {...rest}>
      {children}
    </S.ButtonContainer>
  );
};

export default Button;
