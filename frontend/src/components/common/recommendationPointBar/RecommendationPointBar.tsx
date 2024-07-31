import React from "react";
import Icon from "@/components/common/icon/Icon";
import IconRadioButton from "@/components/common/iconRadioButton/IconRadioButton";
import * as S from "@/components/common/recommendationPointBar/RecommendationPointBar.style";
import IconKind from "@/@types/icon";

interface RecommendationOption {
  id: number;
  text: string;
  value: number;
  icon: IconKind;
}

const recommendationOptions: RecommendationOption[] = [
  { id: 1, text: "추천하지 않아요", value: 1, icon: "thumbDown" },
  { id: 2, text: "추천해요", value: 2, icon: "thumbUp" },
  { id: 3, text: "완전 추천해요", value: 3, icon: "thumbUp" },
];

const RecommendationPointBar = () => {
  return (
    <S.BarContainer>
      {recommendationOptions.map((option) => (
        <IconRadioButton
          key={option.id}
          id={option.id}
          text={option.text}
          name="recommendationOption"
          value={option.value}
        >
          <S.StyledChildren>
            <Icon kind={option.icon} />
          </S.StyledChildren>
        </IconRadioButton>
      ))}
    </S.BarContainer>
  );
};

export default RecommendationPointBar;
