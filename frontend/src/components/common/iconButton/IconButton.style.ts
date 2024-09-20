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
  gap: 0.4rem;
  align-items: center;
  justify-content: center;

  width: 50px;
  height: 50px;

  background-color: ${({ isSelected, theme }) =>
    isSelected ? theme.COLOR.primary1 : "transparent"};
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 15px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular}

  &:active {
    position: relative;
    top: 3px;
    box-shadow: ${({ theme }) => theme.BOX_SHADOW.light}
  }
`;

export const IconButtonText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
