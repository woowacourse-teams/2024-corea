import * as S from "@/components/feedback/keywordOptionButton/KeywordOptionButton.style";
import { theme } from "@/styles/theme";

interface OptionButtonProps {
  selectedOptions?: string[];
  readonly?: boolean;
  onChange?: (options: string[]) => void;
  selectedEvaluationId?: number;
  options: string[];
  color?: string;
}

const KeywordOptionButton = ({
  selectedOptions = [],
  readonly = false,
  onChange,
  options,
  color = theme.COLOR.primary2,
}: OptionButtonProps) => {
  const toggleOption = (text: string) => {
    if (readonly) return;
    const newOptions = selectedOptions.includes(text)
      ? selectedOptions.filter((option) => option !== text)
      : [...selectedOptions, text];

    onChange?.(newOptions);
  };

  return (
    <S.OptionContainer>
      {options.map((text) => (
        <S.ButtonWrapper
          key={text}
          onClick={() => toggleOption(text)}
          isSelected={selectedOptions.includes(text)}
          color={color}
          tabIndex={readonly ? -1 : 0}
        >
          {text}
        </S.ButtonWrapper>
      ))}
    </S.OptionContainer>
  );
};

export default KeywordOptionButton;
