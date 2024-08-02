import styled from "styled-components";

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
  border: ${(props) =>
    props.isSelected
      ? `4px solid ${props.theme.COLOR.primary3}`
      : `2px dashed ${props.theme.COLOR.grey1}`};
  border-radius: 18px;
`;
