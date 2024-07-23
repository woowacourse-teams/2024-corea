import React from "react";
import * as S from "@/components/common/label/Label.style";

export type LabelType = "keyword" | "open" | "close";

interface LabelProps {
  text?: string;
  type: LabelType;
}

const Label = ({ text, type }: LabelProps) => {
  return (
    <S.LabelWrapper type={type}>
      {type === "keyword" && `#${text}`}
      {type === "open" && "모집 중"}
      {type === "close" && "모집 완료"}
    </S.LabelWrapper>
  );
};

export default Label;
