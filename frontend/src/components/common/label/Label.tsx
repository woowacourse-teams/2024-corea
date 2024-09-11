import * as S from "@/components/common/label/Label.style";
import { ThemeType } from "@/styles/theme";

export type LabelType = "keyword" | "open" | "close";
export type LabelSize = keyof ThemeType["TEXT"];

interface LabelProps {
  text?: string;
  type: LabelType;
  size?: LabelSize;
  backgroundColor?: string;
}

const Label = ({ text, type, size = "semiSmall", backgroundColor }: LabelProps) => {
  return (
    <S.LabelWrapper type={type} $size={size} $backgroundColor={backgroundColor}>
      {type === "keyword" && `#${text}`}
      {type === "open" && "모집 중"}
      {type === "close" && "모집 완료"}
    </S.LabelWrapper>
  );
};

export default Label;
