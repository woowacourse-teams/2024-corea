import styled from "styled-components";
import { theme } from "@/styles/theme";

export const SplitterBox = styled.div<{ $size: number; $color: keyof typeof theme.COLOR }>`
  width: 100%;
  height: ${({ $size }) => $size}px;
  background-color: ${({ $color }) => theme.COLOR[$color]};
`;
