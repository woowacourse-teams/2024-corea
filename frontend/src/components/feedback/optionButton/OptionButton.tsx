import React, { useEffect, useState } from "react";
import * as S from "@/components/feedback/optionButton/OptionButton.style";

const bad_options = [
  "방의 목적에 맞게 코드를 작성하지 않았어요",
  "코드를 이해하기 어려웠어요",
  "응답 속도가 느렸어요",
];
const normal_options = [
  "방의 목적에 놓친 부분이 있어요",
  "코드를 이해는 했어요",
  "응답 속도가 적당했어요",
];
const good_options = [
  "방의 목적에 맞게 코드를 잘 작성했어요",
  "코드를 이해하기 쉬웠어요",
  "응답 속도가 빨랐어요",
];

interface OptionButtonProps {
  initialOptions?: string[];
  readonly?: boolean;
  onChange?: (options: string[]) => void;
  selectedEvaluationId?: number;
}

const OptionButton = ({
  initialOptions = [],
  readonly = false,
  onChange,
  selectedEvaluationId,
}: OptionButtonProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>(initialOptions);

  useEffect(() => {
    setSelectedOptions(initialOptions);
  }, [initialOptions]);

  const toggleOption = (text: string) => {
    if (readonly) return;
    setSelectedOptions((prevSelectedOptions) => {
      const newOptions = prevSelectedOptions.includes(text)
        ? prevSelectedOptions.filter((option) => option !== text)
        : [...prevSelectedOptions, text];
      onChange?.(newOptions);
      return newOptions;
    });
  };

  const options = () => {
    if (selectedEvaluationId === undefined) return good_options;
    if (selectedEvaluationId <= 2) return bad_options;
    if (selectedEvaluationId === 3) return normal_options;
    return good_options;
  };

  return (
    <S.OptionContainer>
      {options().map((text) => (
        <S.ButtonWrapper
          key={text}
          onClick={() => toggleOption(text)}
          isSelected={selectedOptions.includes(text)}
        >
          {text}
        </S.ButtonWrapper>
      ))}
    </S.OptionContainer>
  );
};

export default OptionButton;
