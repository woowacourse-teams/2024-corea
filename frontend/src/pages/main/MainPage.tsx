import { Suspense, useState } from "react";
import { useNavigate } from "react-router-dom";
import useSelectedCategory from "@/hooks/common/useSelectedCategory";
import Button from "@/components/common/button/Button";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import Loading from "@/components/common/loading/Loading";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import Banner from "@/components/main/banner/Banner";
import ClosedRoomList from "@/components/main/room/ClosedRoomList";
import OpenedRoomList from "@/components/main/room/OpenedRoomList";
import ParticipatedRoomList from "@/components/main/room/ParticipatedRoomList";
import * as S from "@/pages/main/MainPage.style";
import { Option } from "@/@types/rooms";
import { optionsLoggedIn, optionsLoggedOut } from "@/constants/room";

const MainPage = () => {
  const navigate = useNavigate();
  const isLoggedIn = !!localStorage.getItem("accessToken");
  const options = isLoggedIn ? optionsLoggedIn : optionsLoggedOut;
  const [selectedTab, setSelectedTab] = useState<Option>(options[0]);

  const { selectedCategory, handleSelectedCategory } = useSelectedCategory();

  const renderContent = () => {
    switch (selectedTab) {
      case "참여 중":
        return (
          <Suspense
            fallback={
              <DelaySuspense>
                <Loading />
              </DelaySuspense>
            }
          >
            <ParticipatedRoomList />
          </Suspense>
        );
      case "모집 중":
        return (
          <Suspense
            fallback={
              <DelaySuspense>
                <Loading />
              </DelaySuspense>
            }
          >
            <OpenedRoomList
              selectedCategory={selectedCategory}
              handleSelectedCategory={handleSelectedCategory}
            />
          </Suspense>
        );
      case "모집 마감":
        return (
          <Suspense
            fallback={
              <DelaySuspense>
                <Loading />
              </DelaySuspense>
            }
          >
            <ClosedRoomList
              selectedCategory={selectedCategory}
              handleSelectedCategory={handleSelectedCategory}
            />
          </Suspense>
        );
      default:
        return null;
    }
  };

  return (
    <S.Layout>
      <Banner />

      <Button onClick={() => navigate("/rooms/create")}>방 생성하기</Button>

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
