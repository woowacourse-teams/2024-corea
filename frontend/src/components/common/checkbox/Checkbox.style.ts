import styled from "styled-components";
import { VisuallyHidden } from "@/styles/common";

export const CheckboxLabel = styled.label`
  cursor: pointer;

  position: relative;

  display: flex;
  gap: 0.5rem;
  align-items: center;
`;

export const CheckboxStyle = styled.div<{ checked: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 16px;
  height: 16px;

  background-color: ${({ theme, checked }) => (checked ? theme.COLOR.primary2 : theme.COLOR.white)};
  border: 2px solid ${({ theme }) => theme.COLOR.primary2};
  border-radius: 2px;
`;

export const HiddenCheckbox = styled.input`
  ${VisuallyHidden}
`;

export const CustomCheckbox = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;

  svg {
    color: white;
  }
`;

export const CheckboxText = styled.span`
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
