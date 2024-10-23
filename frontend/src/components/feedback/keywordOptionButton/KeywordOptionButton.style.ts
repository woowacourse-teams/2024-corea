import styled from "styled-components";

interface OptionBoxProps {
  isSelected?: boolean;
  color?: string;
}

export const OptionContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 1.2rem;
`;

export const HiddenRadioInput = styled.input`
  position: absolute;
  opacity: 0;
`;

export const ButtonWrapper = styled.div<OptionBoxProps>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  padding: 0.8rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.black};

  background-color: ${({ theme }) => theme.COLOR.white};
  border: ${(props) =>
    props.isSelected
      ? `2px solid ${props.color || props.theme.COLOR.primary2}`
      : `2px dashed ${props.theme.COLOR.grey1}`};
  border-radius: 18px;

  &:focus {
    outline: "2px rgb(0 95 204) solid";
  }
`;
