import React, { useState } from "react";
import * as S from "@/components/feedback/optionButton/OptionButton.style";

const options = ["작업 속도", "PR 본문 메시지", "코드의 관심사 분리", "리뷰 반영"];

const OptionButton = () => {
  const [selectedOptions, setSelectedOptions] = useState<string[]>([]);

  const toggleOption = (text: string) => {
    setSelectedOptions((prev) =>
      prev.includes(text) ? prev.filter((option) => option !== text) : [...prev, text],
    );
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
