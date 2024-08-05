import { LabelSize, LabelType } from "./Label";
import styled, { css } from "styled-components";

interface LabelWrapperProps {
  type: LabelType;
  $size?: LabelSize;
}

export const LabelWrapper = styled.div<LabelWrapperProps>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: fit-content;
  padding: 0 0.4rem;

  font: ${(props) => props.$size && props.theme.TEXT[props.$size]};
  color: ${({ theme }) => theme.COLOR.black};

  border-radius: 15px;

  ${({ type, theme }) => {
    switch (type) {
      case "keyword":
        return css`
          background-color: ${theme.COLOR.white};
          border: 1px solid ${theme.COLOR.grey1};
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
