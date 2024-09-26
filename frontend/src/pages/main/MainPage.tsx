import { useState } from "react";
import useSelectedCategory from "@/hooks/common/useSelectedCategory";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import Banner from "@/components/main/banner/Banner";
import ClosedRoomList from "@/components/main/room/ClosedRoomList";
import OpenedRoomList from "@/components/main/room/OpenedRoomList";
import ParticipatedRoomList from "@/components/main/room/ParticipatedRoomList";
import ProgressRoomList from "@/components/main/room/ProgressRoomList";
import * as S from "@/pages/main/MainPage.style";
import { Option } from "@/@types/rooms";
import { optionsLoggedIn, optionsLoggedOut } from "@/constants/room";

const MainPage = () => {
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const options = isLoggedIn ? optionsLoggedIn : optionsLoggedOut;
  const [selectedTab, setSelectedTab] = useState<Option>(options[0]);

  const { selectedCategory, handleSelectedCategory } = useSelectedCategory();

  const FilteredRoomList = () => {
    switch (selectedTab) {
      case "참여중":
        return <ParticipatedRoomList />;
      case "진행중":
        return (
          <ProgressRoomList
            selectedCategory={selectedCategory}
            handleSelectedCategory={handleSelectedCategory}
          />
        );
      case "모집중":
        return (
          <OpenedRoomList
            selectedCategory={selectedCategory}
            handleSelectedCategory={handleSelectedCategory}
          />
        );
      case "모집마감":
        return (
          <ClosedRoomList
            selectedCategory={selectedCategory}
            handleSelectedCategory={handleSelectedCategory}
          />
        );
      default:
        return null;
    }
  };

  return (
    <S.Layout>
      <Banner />

      <OptionSelect
        selected={selectedTab}
        options={options}
        handleSelectedOption={(option) => setSelectedTab(option)}
      />

      <FilteredRoomList />
    </S.Layout>
  );
};

export default MainPage;
