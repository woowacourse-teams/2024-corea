import styled from "styled-components";

interface IconRadioButtonBoxProps {
  $isSelected?: boolean;
  $color?: string;
}

export const IconRadioButtonContainer = styled.label`
  cursor: pointer;

  display: flex;
  flex-direction: column;
  gap: 0.6rem;
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
  border: ${({ $color, $isSelected, theme }) =>
    $isSelected
      ? `4px solid ${$color ? $color : theme.COLOR.primary2}`
      : `1px solid ${theme.COLOR.grey1}`};
  border-radius: 50%;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const IconRadioButtonText = styled.span`
  font: ${({ theme }) => theme.TEXT.xSmall};
`;
