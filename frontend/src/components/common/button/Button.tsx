import React from "react";
import { ButtonHTMLAttributes } from "react";
import * as S from "@/components/common/button/Button.style";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "disable";
  size?: "small" | "medium" | "large";
}

const Button = ({ children, variant = "primary", size = "medium", ...rest }: ButtonProps) => {
  return (
    <S.Button $variant={variant} $size={size} {...rest}>
      {children}
    </S.Button>
  );
};

export default Button;
