import { useEffect, useState } from "react";
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
  { text: "아쉬움", value: 1, icon: "bad" },
  { text: "", value: 2, icon: "disappointing" },
  { text: "", value: 3, icon: "average" },
  { text: "", value: 4, icon: "satisfied" },
  { text: "만족", value: 5, icon: "verySatisfied" },
];

interface EvaluationPointBarProps {
  initialOptionId?: number;
  readonly?: boolean;
  onChange?: (value: number) => void;
  color?: string;
  isTabFocusable?: boolean;
}

const EvaluationPointBar = ({
  initialOptionId,
  readonly = false,
  color,
  onChange,
  isTabFocusable = true,
}: EvaluationPointBarProps) => {
  const [selectedOptionId, setSelectedOptionId] = useState<number | undefined>(initialOptionId);

  useEffect(() => {
    if (initialOptionId !== undefined) {
      setSelectedOptionId(initialOptionId);
    }
  }, [initialOptionId]);

  const handleRadioChange = (id: number) => {
    if (readonly) return;
    setSelectedOptionId(id);
    onChange?.(id);
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
          color={color}
          onChange={handleRadioChange}
          tabIndex={isTabFocusable ? 0 : -1}
          onKeyDown={(e) => {
            if (e.key === "Enter") handleRadioChange(option.value);
          }}
          aria-labelledby={`score-${option.value}`}
        >
          <S.StyledChildren>
            <Icon kind={option.icon} aria-hidden={true} />
          </S.StyledChildren>
          <span id={`score-${option.value}`} hidden>{`${option.value}점`}</span>
        </IconRadioButton>
      ))}
    </S.BarContainer>
  );
};

export default EvaluationPointBar;
