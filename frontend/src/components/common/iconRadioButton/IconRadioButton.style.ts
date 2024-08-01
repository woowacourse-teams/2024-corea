import styled, { css } from "styled-components";

interface IconRadioButtonBoxProps {
  isSelected?: boolean;
}

export const IconRadioButtonContainer = styled.label`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const HiddenRadioInput = styled.input`
  position: absolute;
  opacity: 0;
`;

export const IconRadioButtonBox = styled.div<IconRadioButtonBoxProps>`
  display: flex;
  gap: 0.4rem;
  align-items: center;
  justify-content: center;

  width: 50px;
  height: 50px;

  background-color: "transparent";
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 50%;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  ${({ isSelected, theme }) =>
    isSelected &&
    css`
      border: 4px solid ${theme.COLOR.primary3};
    `}
`;

export const IconRadioButtonText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
