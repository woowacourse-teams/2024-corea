import React from "react";
import * as S from "@/components/shared/classificationBadge/ClassificationBadge.style";
import { Classification } from "@/@types/roomInfo";
import { classificationText } from "@/utils/roomInfoUtils";

const ClassificationBadge = ({ text }: { text: Classification }) => {
  return <S.StyledBadge $text={text}>{classificationText(text)}</S.StyledBadge>;
};

export default ClassificationBadge;
