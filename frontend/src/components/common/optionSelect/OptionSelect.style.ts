import styled from "styled-components";

export const OptionSelectContainer = styled.div`
  display: flex;
  flex-direction: row;
  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
`;

export const Option = styled.button<{ $isSelected: boolean }>`
  box-sizing: content-box;
  padding: 0.4rem 1rem;

  font: ${({ theme }) => theme.TEXT.medium};

  background: transparent;
  border-bottom: ${({ $isSelected, theme }) =>
    $isSelected ? `4px solid ${theme.COLOR.primary2}` : "none"};
  outline: none;
`;
