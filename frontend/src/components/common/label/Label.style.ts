import { LabelType } from "./Label";
import styled, { css } from "styled-components";

interface LabelWrapperProps {
  type: LabelType;
}

export const LabelWrapper = styled.div<LabelWrapperProps>`
  display: inline-flex;
  align-items: center;
  justify-content: center;

  padding: 0.2rem 0.6rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.black};

  border-radius: 15px;

  ${({ type, theme }) => {
    switch (type) {
      case "keyword":
        return css`
          background-color: ${theme.COLOR.white};
          border: 2px solid ${theme.COLOR.grey2};
        `;
      case "open":
        return css`
          color: ${theme.COLOR.black};
          background-color: ${theme.COLOR.primary1};
        `;
      case "close":
        return css`
          color: ${theme.COLOR.white};
          background-color: ${theme.COLOR.primary2};
        `;
    }
  }}
`;
