import React, { useState } from "react";
import Icon from "@/components/common/icon/Icon";
import IconRadioButton from "@/components/common/iconRadioButton/IconRadioButton";
import * as S from "@/components/feedback/evaluationPointBar/EvaluationPointBar.style";
import IconKind from "@/@types/icon";

interface EvaluationOption {
  text: string;
  value: number;
  icon: IconKind;
}

const evaluationOptions: EvaluationOption[] = [
  { text: "나쁨", value: 1, icon: "bad" },
  { text: "아쉬움", value: 2, icon: "disappointing" },
  { text: "보통", value: 3, icon: "average" },
  { text: "만족", value: 3, icon: "satisfied" },
  { text: "매우 만족", value: 3, icon: "verySatisfied" },
];

const EvaluationPointBar = () => {
  const [selectedOptionId, setSelectedOptionId] = useState<number | null>(null);

  const handleRadioChange = (id: number) => {
    setSelectedOptionId(id);
  };

  return (
    <S.BarContainer>
      {evaluationOptions.map((option) => (
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

export default EvaluationPointBar;
