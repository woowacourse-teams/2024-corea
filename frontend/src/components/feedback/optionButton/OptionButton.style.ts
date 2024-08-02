import styled, { css } from "styled-components";

interface OptionButtonBoxProps {
  isSelected?: boolean;
}

export const OptionContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.6rem;
`;

export const ButtonWrapper = styled.button<OptionButtonBoxProps>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 0.4rem 0.8rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};

  background-color: ${({ theme }) => theme.COLOR.white};
  border: 2px dashed ${({ theme }) => theme.COLOR.grey1};
  border-radius: 18px;

  ${({ isSelected, theme }) =>
    isSelected &&
    css`
      border: 4px solid ${theme.COLOR.primary3};
    `}
`;
