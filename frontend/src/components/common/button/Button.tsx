import { ButtonHTMLAttributes, forwardRef } from "react";
import * as S from "@/components/common/button/Button.style";
import { spinner } from "@/assets";

export type ButtonVariant = "default" | "primary" | "secondary" | "disable" | "confirm" | "error";
export type ButtonSize = "xSmall" | "small" | "medium" | "large";

export interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
  outline?: boolean;
  isLoading?: boolean;
}

const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  (
    {
      children,
      variant = "primary",
      size = "medium",
      disabled = false,
      outline = false,
      isLoading = false,
      ...rest
    },
    ref,
  ) => {
    return (
      <S.ButtonContainer
        ref={ref}
        $variant={disabled ? "disable" : variant}
        $size={size}
        $outline={outline}
        disabled={disabled || isLoading}
        tabIndex={disabled ? -1 : 0}
        {...rest}
      >
        {isLoading ? <S.LoadingSpinner src={spinner} /> : children}
      </S.ButtonContainer>
    );
  },
);

Button.displayName = "Button";

export default Button;
