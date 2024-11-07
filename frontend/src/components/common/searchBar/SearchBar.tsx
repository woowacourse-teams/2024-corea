import * as S from "./SearchBar.style";
import { InputHTMLAttributes } from "react";
import Icon from "@/components/common/icon/Icon";

interface SearchBarProps extends InputHTMLAttributes<HTMLInputElement> {
  value: string;
  handleValue: (e: React.ChangeEvent<HTMLInputElement>) => void;
  handleSearch: () => void;
}

const SearchBar = ({ value, handleValue, handleSearch, ...props }: SearchBarProps) => {
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      handleSearch();
    }
  };

  return (
    <S.SearchBarContainer>
      <S.SearchBar value={value} onChange={handleValue} onKeyDown={handleKeyDown} {...props} />
      <S.SearchIconWrapper onClick={handleSearch}>
        <Icon kind="search" />
      </S.SearchIconWrapper>
    </S.SearchBarContainer>
  );
};

export default SearchBar;
