import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/common/label/Label.style";
import { ThemeType, theme } from "@/styles/theme";

export type LabelType =
  | "KEYWORD"
  | "PARTICIPATED"
  | "OPEN"
  | "PROGRESS"
  | "CLOSE"
  | "FAIL"
  | "MANAGER";

export type LabelSize = keyof ThemeType["TEXT"];

interface LabelProps {
  text?: string;
  managerText?: string;
  type: LabelType;
  size?: LabelSize;
  backgroundColor?: string;
}

const Label = ({ text, managerText, type, size = "semiSmall", backgroundColor }: LabelProps) => {
  return (
    <S.LabelWrapper type={type} $size={size} $backgroundColor={backgroundColor}>
      {type === "KEYWORD" && `#${text}`}
      {type === "PARTICIPATED" && "참여"}
      {type === "OPEN" && "모집 중"}
      {type === "PROGRESS" && "진행 중"}
      {type === "CLOSE" && "종료"}
      {type === "FAIL" && "매칭 실패"}
      {type === "MANAGER" && (
        <>
          <Icon kind="person" size="1.6rem" color={theme.COLOR.grey4} />
          {managerText}
        </>
      )}
    </S.LabelWrapper>
  );
};

export default Label;
