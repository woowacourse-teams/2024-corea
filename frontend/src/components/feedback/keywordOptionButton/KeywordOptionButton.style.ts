import styled from "styled-components";

interface OptionButtonBoxProps {
  isSelected?: boolean;
  color?: string;
}

export const OptionContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
`;

export const ButtonWrapper = styled.button<OptionButtonBoxProps>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 0.5rem 0.9rem;

  font: ${({ theme }) => theme.TEXT.small};

  background-color: ${({ theme }) => theme.COLOR.white};
  border-radius: 18px;
  outline: ${(props) =>
    props.isSelected
      ? `4px solid ${props.color || props.theme.COLOR.primary2}`
      : `2px dashed ${props.theme.COLOR.grey1}`};
`;
