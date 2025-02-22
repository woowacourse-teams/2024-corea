import { HTMLAttributes } from "react";
import * as S from "@/components/common/splitter/Splitter.style";
import { theme } from "@/styles/theme";

interface SplitterProps extends HTMLAttributes<HTMLDivElement> {
  size: number;
  color: keyof typeof theme.COLOR;
}

export const Splitter = ({ size, color, ...props }: SplitterProps) => {
  return <S.SplitterBox $size={size} $color={color} {...props} />;
};
