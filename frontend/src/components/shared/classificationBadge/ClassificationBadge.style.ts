import styled from "styled-components";
import { Classification } from "@/@types/roomInfo";
import { TYPE_CLASSIFICATION } from "@/styles/common";

export const StyledBadge = styled.div<{ $text: Classification }>`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${(props) => TYPE_CLASSIFICATION[props.$text]};
  text-shadow: 1px 1px 2px rgb(0 0 0 / 50%);

  background-color: ${({ theme }) => theme.COLOR.grey3};
  box-shadow: 0 2px 4px rgb(0 0 0 / 15%);
`;
