import styled from "styled-components";

export const DropdownContainer = styled.div`
  position: relative;
  width: 140px;
  height: 32px;
`;

export const DropdownToggle = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 100%;
  padding: 0.4rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  color: ${({ theme }) => theme.COLOR.grey4};

  border: 1px solid ${({ theme }) => theme.COLOR.grey2};
  border-radius: 4px;
`;

export const DropdownMenu = styled.div<{ show: boolean }>`
  position: absolute;
  z-index: 1;
  right: 0;

  display: ${({ show }) => (show ? "flex" : "none")};
  flex-direction: column;

  width: 100%;

  background-color: white;
  border: 1px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 4px;
`;

export const DropdownItemWrapper = styled.ul`
  margin: 0.5rem;
`;

export const DropdownItem = styled.li`
  cursor: pointer;
  display: flex;
  align-items: center;
  padding: 0.4rem;

  &:hover {
    background-color: ${({ theme }) => theme.COLOR.grey0};
  }

  span {
    font: ${({ theme }) => theme.TEXT.semiSmall};
    color: ${({ theme }) => theme.COLOR.grey4};
  }
`;
