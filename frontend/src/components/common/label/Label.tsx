import * as S from "@/components/common/label/Label.style";
import { ThemeType } from "@/styles/theme";

export type LabelType = "KEYWORD" | "OPEN" | "CLOSE" | "PROGRESS" | "FAIL";
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
      {type === "KEYWORD" && `#${text}`}
      {type === "OPEN" && "모집 중"}
      {type === "CLOSE" && "종료"}
      {type === "PROGRESS" && "진행 중"}
      {type === "FAIL" && "매칭 실패"}
    </S.LabelWrapper>
  );
};

export default Label;
