import styled from "styled-components";
import { checkIcon } from "@/assets";

export const CheckboxWrapper = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
`;

export const CheckboxStyle = styled.input`
  cursor: pointer;

  width: 16px;
  height: 16px;

  appearance: none;
  background-color: ${({ theme }) => theme.COLOR.white};
  border: 2px solid ${({ theme }) => theme.COLOR.grey1};
  border-radius: 3px;

  &:checked {
    background-color: ${({ theme }) => theme.COLOR.primary2};
    background-image: url(${checkIcon});
    background-repeat: no-repeat;
    background-position: center;
    background-size: 12px;
    border-color: ${({ theme }) => theme.COLOR.primary2};
  }

  &:hover {
    border-color: ${({ theme }) => theme.COLOR.primary2};
  }
`;

export const StyledLabel = styled.label`
  cursor: pointer;
  font: ${({ theme }) => theme.TEXT.semiSmall};
`;
