import { useNavigate } from "react-router-dom";
import Icon from "@/components/common/icon/Icon";
import Banner from "@/components/main/banner/Banner";
import Content from "@/components/main/content/Content";
import * as S from "@/pages/main/MainPage.style";

const MainPage = () => {
  const navigate = useNavigate();
  return (
    <S.Layout>
      <Banner />

      <S.RoomCreateButton onClick={() => navigate("/rooms/create")}>
        <Icon kind="add" size={30} />
        <span>방 생성하기</span>
      </S.RoomCreateButton>

      <Content />
    </S.Layout>
  );
};

export default MainPage;
