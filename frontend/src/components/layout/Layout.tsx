import { Outlet } from "react-router-dom";
import Header from "@/components/common/header/Header";
import * as S from "@/components/layout/Layout.style";
import RouteChangeTracker from "@/RouteChangeTracker";

const Layout = () => {
  RouteChangeTracker();

  return (
    <>
      <Header />
      <S.ContentContainer>
        <Outlet />
      </S.ContentContainer>
    </>
  );
};

export default Layout;
