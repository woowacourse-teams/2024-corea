import React, { useEffect, useState } from "react";
import * as S from "@/components/feedback/optionButton/OptionButton.style";

const options = ["작업 속도", "PR 본문 메시지", "코드의 관심사 분리", "리뷰 반영"];

interface OptionButtonProps {
  initialOptions?: string[];
  readonly?: boolean;
  onChange?: (options: string[]) => void;
}

const OptionButton = ({ initialOptions = [], readonly = false, onChange }: OptionButtonProps) => {
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
        >
          {text}
        </S.ButtonWrapper>
      ))}
    </S.OptionContainer>
  );
};

export default OptionButton;
