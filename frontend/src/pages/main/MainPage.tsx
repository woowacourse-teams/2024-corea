import { useState } from "react";
import useSelectedCategory from "@/hooks/common/useSelectedCategory";
import {
  useFetchParticipatedRoomList,
  useInfiniteFetchRoomList,
} from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Dropdown, { DropdownItem } from "@/components/common/dropdown/Dropdown";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import Banner from "@/components/main/banner/Banner";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/pages/main/MainPage.style";
import { Option } from "@/@types/rooms";
import QUERY_KEYS from "@/apis/queryKeys";
import { getClosedRoomList, getOpenedRoomList } from "@/apis/rooms.api";
import { optionsLoggedIn, optionsLoggedOut } from "@/constants/room";

export const dropdownItems: DropdownItem[] = [
  { text: "ALL", value: "all" },
  { text: "ANDROID", value: "an" },
  { text: "BACKEND", value: "be" },
  { text: "FRONTEND", value: "fe" },
];

const MainPage = () => {
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const options = isLoggedIn ? optionsLoggedIn : optionsLoggedOut;
  const [selectedTab, setSelectedTab] = useState<Option>(options[0]);

  const { selectedCategory, handleSelectedCategory } = useSelectedCategory();
  const { data: participatedRoomList } = useFetchParticipatedRoomList();

  const {
    data: openedRoomList,
    fetchNextPage: fetchNextOpenedPage,
    hasNextPage: hasNextOpenedPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.OPENED_ROOM_LIST, selectedCategory],
    getRoomList: getOpenedRoomList,
    classification: selectedCategory,
  });

  const {
    data: closedRoomList,
    fetchNextPage: fetchNextClosedPage,
    hasNextPage: hasNextClosedPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST, selectedCategory],
    getRoomList: getClosedRoomList,
    classification: selectedCategory,
  });

  const openedRooms = openedRoomList?.pages.flatMap((page) => page.rooms) || [];
  const closedRooms = closedRoomList?.pages.flatMap((page) => page.rooms) || [];

  const renderContent = () => {
    switch (selectedTab) {
      case "참여 중":
        return (
          <ContentSection title="">
            <RoomList roomList={participatedRoomList.rooms} roomType="participated" />
          </ContentSection>
        );
      case "모집 중":
        return (
          <ContentSection title="">
            <S.DropdownWrapper>
              <Dropdown
                dropdownItems={dropdownItems}
                selectedCategory={selectedCategory}
                onSelectCategory={handleSelectedCategory}
              />
            </S.DropdownWrapper>
            <RoomList
              roomList={openedRooms}
              hasNextPage={hasNextOpenedPage}
              onLoadMore={() => fetchNextOpenedPage()}
              roomType="opened"
            />
          </ContentSection>
        );
      case "모집 마감":
        return (
          <ContentSection title="">
            <S.DropdownWrapper>
              <Dropdown
                dropdownItems={dropdownItems}
                selectedCategory={selectedCategory}
                onSelectCategory={handleSelectedCategory}
              />
            </S.DropdownWrapper>
            <RoomList
              roomList={closedRooms}
              hasNextPage={hasNextClosedPage}
              onLoadMore={() => fetchNextClosedPage()}
              roomType="closed"
            />
          </ContentSection>
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

      {renderContent()}
    </S.Layout>
  );
};

export default MainPage;
