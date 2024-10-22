import styled from "styled-components";
import { VisuallyHidden } from "@/styles/common";

export const CheckboxLabel = styled.label<{ readonly: boolean }>`
  cursor: pointer;
  cursor: ${({ readonly }) => (readonly ? "not-allowed" : "pointer")};

  position: relative;

  display: flex;
  gap: 0.5rem;
  align-items: center;

  color: ${({ theme, readonly }) => (readonly ? theme.COLOR.grey2 : theme.COLOR.black)};
`;

export const CheckboxStyle = styled.div<{ checked: boolean; readonly: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 16px;
  height: 16px;

  background-color: ${({ theme, checked, readonly }) => {
    if (readonly) return theme.COLOR.grey2;
    if (checked) return theme.COLOR.primary2;
    return theme.COLOR.white;
  }};
  border: 2px solid
    ${({ theme, readonly }) => (readonly ? theme.COLOR.grey2 : theme.COLOR.primary2)};
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
