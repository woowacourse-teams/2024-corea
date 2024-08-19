import { useEffect, useState } from "react";
import * as S from "@/components/feedback/keywordOptionButton/KeywordOptionButton.style";
import { theme } from "@/styles/theme";

interface OptionButtonProps {
  initialOptions?: string[];
  readonly?: boolean;
  onChange?: (options: string[]) => void;
  selectedEvaluationId?: number;
  options: string[];
  color?: string;
}

const KeywordOptionButton = ({
  initialOptions = [],
  readonly = false,
  onChange,
  options,
  color = theme.COLOR.primary3,
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

  return (
    <S.OptionContainer>
      {options.map((text) => (
        <S.ButtonWrapper
          key={text}
          onClick={() => toggleOption(text)}
          isSelected={selectedOptions.includes(text)}
          color={color}
        >
          {text}
        </S.ButtonWrapper>
      ))}
    </S.OptionContainer>
  );
};

export default KeywordOptionButton;
