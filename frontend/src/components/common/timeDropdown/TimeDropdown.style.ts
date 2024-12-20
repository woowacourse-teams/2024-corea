import styled from "styled-components";
import { Z_INDEX } from "@/styles/zIndex";

//TimeDropdown
export const TimeDropdownContainer = styled.section`
  position: relative;
  width: 100px;
`;

export const TimeDropdownToggle = styled.input<{ $error: boolean }>`
  cursor: pointer;

  width: 100%;
  padding: 0.6rem 1.1rem;

  font: ${({ theme }) => theme.TEXT.semiSmall};
  text-align: center;
  letter-spacing: 0.2rem;

  border: 1px solid ${({ theme, $error }) => ($error ? theme.COLOR.error : theme.COLOR.grey1)};
  border-radius: 6px;
  outline-color: ${({ theme }) => theme.COLOR.black};
`;

//TimePicker
export const TimePickerWrapper = styled.div`
  position: absolute;
  z-index: ${Z_INDEX.dropdown};

  display: flex;
  gap: 1rem;
  align-items: flex-start;

  width: 100px;
  padding: 1rem;

  background-color: ${({ theme }) => theme.COLOR.white};
  border-radius: 8px;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};
`;

export const TimeSelector = styled.div`
  overflow: hidden auto;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  align-items: center;

  width: 100%;
  height: 300px;

  &::-webkit-scrollbar {
    display: none;
  }
`;

export const TimeButton = styled.button<{ isActive: boolean }>`
  cursor: pointer;

  width: 100%;
  padding: 5px;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ isActive, theme }) => (isActive ? theme.COLOR.white : theme.COLOR.black)};

  background-color: ${({ isActive, theme }) =>
    isActive ? theme.COLOR.primary2 : theme.COLOR.white};
  border-radius: 4px;

  &:hover {
    background-color: ${({ isActive, theme }) =>
      isActive ? theme.COLOR.primary2 : theme.COLOR.grey0};
  }
`;
