import React from "react";
import { useState } from "react";
import Icon from "@/components/common/icon/Icon";
import { Input } from "@/components/common/input/Input";
import * as S from "@/components/common/keyword/Keyword.style";

interface KeywordProps {
  currentKeywords: string[];
  onKeywordsChange: (keywords: string[]) => void;
  error: boolean;
}

const Keyword = ({ currentKeywords, onKeywordsChange, error }: KeywordProps) => {
  const [keyword, setKeyword] = useState<string>("");

  const removeKeyword = (index: number) => {
    const updatedKeywords = currentKeywords.filter((_, i) => i !== index);
    onKeywordsChange(updatedKeywords);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKeyword(e.target.value);
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      e.preventDefault();
      const trimmedKeyword = keyword.trim();

      if (trimmedKeyword === "") return;

      if (trimmedKeyword.includes("⠀") || currentKeywords.includes(trimmedKeyword)) {
        setKeyword("");
        return;
      }

      if (e.nativeEvent.isComposing === false) {
        onKeywordsChange([...currentKeywords, trimmedKeyword]);
        setKeyword("");
      }
    }
  };

  return (
    <S.KeywordContainer>
      {currentKeywords.length !== 0 && (
        <S.KeywordLabelContainer>
          {currentKeywords.map((keyword, index) => (
            <S.KeywordButton
              key={keyword}
              onClick={() => removeKeyword(index)}
              title="키워드를 클릭하면 삭제돼요"
            >
              {keyword}
              <Icon kind="delete" size="2.2rem" />
            </S.KeywordButton>
          ))}
        </S.KeywordLabelContainer>
      )}
      <Input
        type="text"
        placeholder="Enter키를 누르면 키워드가 생성돼요"
        value={keyword}
        showCharCount={true}
        maxLength={20}
        onChange={handleInputChange}
        onKeyDown={handleKeyDown}
        error={error}
      />
    </S.KeywordContainer>
  );
};

export default Keyword;
