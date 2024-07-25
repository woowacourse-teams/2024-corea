import styled from "styled-components";

export const IconButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  &:hover {
    transform: scale(1.05);
  }
`;

export const IconButtonBox = styled.button<{ isSelected: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 50px;
  height: 50px;

  background-color: ${({ isSelected, theme }) =>
    isSelected ? theme.COLOR.primary1 : "transparent"};
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: 0 4px 4px rgb(0 0 0 / 10%);

  &:active {
    position: relative;
    top: 3px;
    box-shadow: 0 1px 1px rgb(0 0 0 / 10%);
  }
`;

export const IconButtonText = styled.span`
  margin-top: 0.4rem;
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
