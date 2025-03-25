import * as S from "./Content.style";
import FilterBar from "./FilterBar";
import FilteredRoomList from "./FilteredRoomList";
import { type ChangeEvent, useEffect, useState } from "react";
import useSelectedCategory from "@/hooks/common/useSelectedCategory";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import { Option } from "@/@types/rooms";
import { optionsLoggedIn, optionsLoggedOut } from "@/constants/room";

const Content = () => {
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const options = isLoggedIn ? optionsLoggedIn : optionsLoggedOut;
  const [selectedTab, setSelectedTab] = useState<Option>(options[0]);

  const { selectedCategory, handleSelectedCategory } = useSelectedCategory();
  const [searchInput, setSearchInput] = useState("");

  useEffect(() => {
    setSearchInput("");
  }, [selectedTab, selectedCategory]);

  const handleSearchInput = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchInput(e.target.value);
  };

  return (
    <S.ContentContainer>
      <OptionSelect
        selected={selectedTab}
        options={options}
        handleSelectedOption={(option) => setSelectedTab(option)}
      />

      {selectedTab !== "참여중" && (
        <FilterBar
          selectedCategory={selectedCategory}
          onSelectCategory={handleSelectedCategory}
          searchInput={searchInput}
          onSearchInputChange={handleSearchInput}
        />
      )}

      <FilteredRoomList
        selectedTab={selectedTab}
        selectedCategory={selectedCategory}
        searchInput={searchInput}
      />
    </S.ContentContainer>
  );
};

export default Content;
