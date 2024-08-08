import React, { useEffect, useState } from "react";
import * as S from "@/components/feedback/keywordOptionButton/KeywordOptionButton.style";
import {
  BAD_KEYWORD_OPTIONS,
  GOOD_KEYWORD_OPTIONS,
  NORMAL_KEYWORD_OPTIONS,
} from "@/constants/feedback";

const getKeywordOptions = (selectedEvaluationId: number | undefined) => {
  if (selectedEvaluationId === undefined) return GOOD_KEYWORD_OPTIONS;
  if (selectedEvaluationId <= 2) return BAD_KEYWORD_OPTIONS;
  if (selectedEvaluationId === 3) return NORMAL_KEYWORD_OPTIONS;
  return GOOD_KEYWORD_OPTIONS;
};

interface OptionButtonProps {
  initialOptions?: string[];
  readonly?: boolean;
  onChange?: (options: string[]) => void;
  selectedEvaluationId?: number;
}

const KeywordOptionButton = ({
  initialOptions = [],
  readonly = false,
  onChange,
  selectedEvaluationId,
}: OptionButtonProps) => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>(initialOptions);
  const options = getKeywordOptions(selectedEvaluationId);

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

  return (
    <S.OptionContainer>
      {options.map((text) => (
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

export default KeywordOptionButton;
