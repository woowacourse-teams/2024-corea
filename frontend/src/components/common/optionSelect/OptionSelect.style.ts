import styled from "styled-components";

export const OptionSelectContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1rem;
`;

export const Option = styled.button<{ $isSelected: boolean }>`
  box-sizing: content-box;

  font: ${({ theme }) => theme.TEXT.small};

  background: transparent;
  border-bottom: ${({ $isSelected, theme }) =>
    $isSelected ? `0.5rem solid ${theme.COLOR.primary2}` : "none"};
  outline: none;
`;
