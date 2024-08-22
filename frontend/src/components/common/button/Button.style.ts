import { css, styled } from "styled-components";

export const ButtonContainer = styled.button<{
  $variant: "primary" | "secondary" | "disable" | "lightBlue" | "error";
  $size: "xSmall" | "small" | "medium" | "large";
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
  lightBlue: css`
    background-color: ${({ theme }) => theme.COLOR.lightGrass};
  `,
  disable: css`
    background-color: ${({ theme }) => theme.COLOR.grey1};
  `,
  error: css`
    background-color: ${({ theme }) => theme.COLOR.error};
  `,
};

const sizeStyles = {
  xSmall: css`
    width: 90px;
    padding: 0.1rem 0;
    font: ${({ theme }) => theme.TEXT.small};
    border-radius: 4px;
  `,
  small: css`
    width: 120px;
    padding: 0.1rem 0;
    font: ${({ theme }) => theme.TEXT.small};
    border-radius: 4px;
  `,
  medium: css`
    width: 180px;
    height: 30px;
    font: ${({ theme }) => theme.TEXT.medium};
    border-radius: 4px;
  `,
  large: css`
    width: 100%;
    height: 40px;
    font: ${({ theme }) => theme.TEXT.medium};
    border-radius: 4px;
  `,
};
