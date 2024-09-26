import Banner from "@/components/main/banner/Banner";
import Content from "@/components/main/content/Content";
import * as S from "@/pages/main/MainPage.style";

const MainPage = () => {
  return (
    <S.Layout>
      <Banner />
      <Content />
    </S.Layout>
  );
};

export default MainPage;
