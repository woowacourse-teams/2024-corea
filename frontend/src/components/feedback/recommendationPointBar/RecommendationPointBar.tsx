import React, { useEffect, useState } from "react";
import Icon from "@/components/common/icon/Icon";
import IconRadioButton from "@/components/common/iconRadioButton/IconRadioButton";
import * as S from "@/components/feedback/recommendationPointBar/RecommendationPointBar.style";
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

interface RecommendationPointBarProps {
  initialOptionId?: number;
  readonly?: boolean;
  onChange?: (value: number) => void;
}

const RecommendationPointBar = ({
  initialOptionId,
  readonly = false,
  onChange,
}: RecommendationPointBarProps) => {
  const [selectedOptionId, setSelectedOptionId] = useState<number | undefined>(initialOptionId);

  useEffect(() => {
    setSelectedOptionId(initialOptionId);
  }, [initialOptionId]);

  const handleRadioChange = (id: number) => {
    if (readonly) return;
    setSelectedOptionId(id);
    onChange?.(id);
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
