import * as S from "./FilterBar.style";
import React, { type ChangeEvent } from "react";
import Dropdown from "@/components/common/dropdown/Dropdown";
import SearchBar from "@/components/common/searchBar/SearchBar";
import { dropdownItems } from "@/constants/roomDropdownItems";

interface FilterBarProps {
  selectedCategory: string;
  onSelectCategory: (category: string) => void;
  searchInput: string;
  onSearchInputChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

const FilterBar = ({
  selectedCategory,
  onSelectCategory,
  searchInput,
  onSearchInputChange,
}: FilterBarProps) => {
  return (
    <S.FilterWrapper>
      <Dropdown
        name="포지션 분류"
        dropdownItems={dropdownItems}
        selectedCategory={selectedCategory}
        onSelectCategory={onSelectCategory}
      />
      <S.SearchBarWrapper>
        <SearchBar
          value={searchInput}
          handleValue={onSearchInputChange}
          placeholder="제목을 입력해주세요"
        />
      </S.SearchBarWrapper>
    </S.FilterWrapper>
  );
};

export default FilterBar;
