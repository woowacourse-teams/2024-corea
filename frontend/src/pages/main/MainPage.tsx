import { useNavigate } from "react-router-dom";
import Icon from "@/components/common/icon/Icon";
import Banner from "@/components/main/banner/Banner";
import MainContentLayout from "@/components/main/content/MainContentLayout";
import * as S from "@/pages/main/MainPage.style";

const MainPage = () => {
  const navigate = useNavigate();
  return (
    <S.Layout>
      <Banner />

      <MainContentLayout />

      <S.RoomCreateButton onClick={() => navigate("/rooms/create")}>
        <Icon kind="add" size={30} />
        <span>방 생성하기</span>
      </S.RoomCreateButton>
    </S.Layout>
  );
};

export default MainPage;
