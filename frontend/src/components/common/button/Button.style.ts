import { ButtonSize, ButtonVariant } from "./Button";
import { css, styled } from "styled-components";
import { ThemeType } from "@/styles/theme";

export const ButtonContainer = styled.button<{
  $variant: ButtonVariant;
  $size: ButtonSize;
  $outline?: boolean;
}>`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;

  ${({ $variant, $outline }) => variantStyles($variant, $outline)}
  ${({ $size }) => sizeStyles[$size]}

  ${({ $variant }) =>
    $variant !== "disable"
      ? css`
          &:hover {
            cursor: pointer;
            opacity: 0.8;
          }
        `
      : css`
          cursor: default;
        `}
`;

const variantStyles = (variant: ButtonVariant, outline?: boolean) => {
  return css`
    ${outline
      ? css`
          color: ${({ theme }) => getColor(theme, variant)};
          background-color: ${({ theme }) => theme.COLOR.white};
          border: 2px solid ${({ theme }) => getColor(theme, variant)};
        `
      : css`
          color: ${({ theme }) =>
            variant === "default" || variant === "confirm" ? theme.COLOR.black : theme.COLOR.white};
          background-color: ${({ theme }) => getColor(theme, variant)};
          border: 2px solid ${({ theme }) => getColor(theme, variant)};
        `}
  `;
};

const getColor = (theme: ThemeType, variant: ButtonVariant) => {
  switch (variant) {
    case "default":
      return theme.COLOR.black;
    case "primary":
      return theme.COLOR.primary2;
    case "secondary":
      return theme.COLOR.secondary;
    case "disable":
      return theme.COLOR.grey1;
    case "confirm":
      return theme.COLOR.lightGrass;
    case "error":
      return theme.COLOR.error;
    default:
      return theme.COLOR.black;
  }
};

const sizeStyles = {
  xSmall: css`
    width: fit-content;
    max-width: 120px;
    padding: 1rem 0.5rem;

    font: ${({ theme }) => theme.TEXT.semiSmall};

    border-radius: 4px;
  `,
  small: css`
    width: 120px;
    padding: 1rem;
    font: ${({ theme }) => theme.TEXT.semiSmall};
    border-radius: 4px;
  `,
  medium: css`
    width: 180px;
    padding: 1rem;
    font: ${({ theme }) => theme.TEXT.medium};
    border-radius: 4px;
  `,
  large: css`
    width: 100%;
    padding: 1rem;
    font: ${({ theme }) => theme.TEXT.medium};
    border-radius: 4px;
  `,
};
