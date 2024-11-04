import { ButtonHTMLAttributes, forwardRef } from "react";
import * as S from "@/components/common/button/Button.style";

export type ButtonVariant = "default" | "primary" | "secondary" | "disable" | "confirm" | "error";
export type ButtonSize = "xSmall" | "small" | "medium" | "large";

export interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
  outline?: boolean;
}

const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  (
    { children, variant = "primary", size = "medium", disabled = false, outline = false, ...rest },
    ref,
  ) => {
    return (
      <S.ButtonContainer
        ref={ref}
        $variant={disabled ? "disable" : variant}
        $size={size}
        $outline={outline}
        disabled={disabled}
        tabIndex={disabled ? -1 : 0}
        {...rest}
      >
        {children}
      </S.ButtonContainer>
    );
  },
);

Button.displayName = "Button";

export default Button;
