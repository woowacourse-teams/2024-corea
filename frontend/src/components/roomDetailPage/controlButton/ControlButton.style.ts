import styled from "styled-components";
import { Z_INDEX } from "@/styles/zIndex";

export const ControlButtonContainer = styled.div`
  position: relative;
`;

export const IconWrapper = styled.div<{ $isOpen: boolean }>`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 40px;
  height: 40px;

  background-color: ${({ theme, $isOpen }) => $isOpen && theme.COLOR.grey0};
  border-radius: 50%;

  &:hover {
    background-color: ${({ theme }) => theme.COLOR.grey0};
  }
`;

export const DropdownMenu = styled.div`
  position: absolute;
  z-index: ${Z_INDEX.dropdown};
  right: 0;

  display: flex;
  flex-direction: column;

  min-width: 180px;
  padding: 1rem;

  background-color: white;
  border: 1px solid ${({ theme }) => theme.COLOR.grey2};
  border-radius: 5px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const DropdownItemWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin: 0.5rem;
`;

export const DropdownItem = styled.li`
  cursor: pointer;

  display: flex;
  gap: 0.3rem;
  align-items: center;

  padding: 0.5rem;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey4};

  &:hover {
    font: ${({ theme }) => theme.TEXT.small_bold};
    background-color: ${({ theme }) => theme.COLOR.grey0};
  }
`;

export const Divider = styled.div`
  width: 100%;
  height: 1px;
  background-color: ${({ theme }) => theme.COLOR.grey2};
`;
