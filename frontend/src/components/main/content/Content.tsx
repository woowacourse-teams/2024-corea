import { useState } from "react";
import useSelectedCategory from "@/hooks/common/useSelectedCategory";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import ClosedRoomList from "@/components/main/room/ClosedRoomList";
import OpenedRoomList from "@/components/main/room/OpenedRoomList";
import ParticipatedRoomList from "@/components/main/room/ParticipatedRoomList";
import ProgressRoomList from "@/components/main/room/ProgressRoomList";
import { Option } from "@/@types/rooms";
import { optionsLoggedIn, optionsLoggedOut } from "@/constants/room";

const Content = () => {
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
    <main>
      <OptionSelect
        selected={selectedTab}
        options={options}
        handleSelectedOption={(option) => setSelectedTab(option)}
      />
      <FilteredRoomList />
    </main>
  );
};

export default Content;
