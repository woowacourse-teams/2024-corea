import React, { useState } from "react";
import Icon from "@/components/common/icon/Icon";
import IconRadioButton from "@/components/common/iconRadioButton/IconRadioButton";
import * as S from "@/components/common/recommendationPointBar/RecommendationPointBar.style";
import IconKind from "@/@types/icon";

interface RecommendationOption {
  text: string;
  value: number;
  icon: IconKind;
}

const recommendationOptions: RecommendationOption[] = [
  { text: "추천하지 않아요", value: 1, icon: "thumbDown" },
  { text: "추천해요", value: 2, icon: "thumbUp" },
  { text: "완전 추천해요", value: 3, icon: "thumbUp" },
];

const RecommendationPointBar = () => {
  const [selectedOptionId, setSelectedOptionId] = useState<number | null>(null);

  const handleRadioChange = (id: number) => {
    setSelectedOptionId(id);
  };

  return (
    <S.BarContainer>
      {recommendationOptions.map((option) => (
        <IconRadioButton
          key={option.value}
          text={option.text}
          name="recommendationOption"
          value={option.value}
          isSelected={selectedOptionId === option.value}
          onChange={handleRadioChange}
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
