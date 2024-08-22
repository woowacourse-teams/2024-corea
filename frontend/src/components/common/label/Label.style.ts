import { LabelSize, LabelType } from "./Label";
import styled, { css } from "styled-components";

interface LabelWrapperProps {
  type: LabelType;
  $size?: LabelSize;
  $backgroundColor?: string;
}

export const LabelWrapper = styled.div<LabelWrapperProps>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: fit-content;
  height: fit-content;
  padding: 0.4rem 1rem;

  font: ${(props) => props.$size && props.theme.TEXT[props.$size]};
  color: ${({ theme }) => theme.COLOR.black};

  border-radius: 15px;

  ${({ $backgroundColor, type, theme }) => {
    switch (type) {
      case "keyword":
        return css`
          background-color: ${$backgroundColor || theme.COLOR.white};
          border: 1px solid ${theme.COLOR.grey0};
        `;
      case "open":
        return css`
          color: ${theme.COLOR.black};
          background-color: ${theme.COLOR.primary1};
          border: 1px solid ${theme.COLOR.primary1};
        `;
      case "close":
        return css`
          color: ${theme.COLOR.white};
          background-color: ${theme.COLOR.primary2};
          border: 1px solid ${theme.COLOR.primary2};
        `;
    }
  }}
`;
