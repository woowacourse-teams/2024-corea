import { ButtonSize, ButtonVariant } from "./Button";
import { css, styled } from "styled-components";

export const ButtonContainer = styled.button<{
  $variant: ButtonVariant;
  $size: ButtonSize;
}>`
  display: flex;
  align-items: center;
  justify-content: center;

  color: ${({ theme }) => theme.COLOR.white};
  text-align: center;

  ${(props) => variantStyles[props.$variant]}
  ${(props) => sizeStyles[props.$size]}

  ${(props) =>
    props.$variant !== "disable"
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

const variantStyles = {
  primary: css`
    background-color: ${({ theme }) => theme.COLOR.primary2};
  `,
  secondary: css`
    background-color: ${({ theme }) => theme.COLOR.secondary};
  `,
  disable: css`
    background-color: ${({ theme }) => theme.COLOR.grey1};
  `,
  confirm: css`
    background-color: ${({ theme }) => theme.COLOR.primary3};
  `,
  error: css`
    background-color: ${({ theme }) => theme.COLOR.error};
  `,
};

const sizeStyles = {
  xSmall: css`
    width: fit-content;
    max-width: 110px;
    padding: 1rem;

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
