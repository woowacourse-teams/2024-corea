import { useEffect } from "react";
import { Outlet, useLocation } from "react-router-dom";
import SentryTotalBoundary from "@/components/common/errorBoundary/SentryTotalBoundary";
import Header from "@/components/common/header/Header";
import * as S from "@/components/layout/Layout.style";
import RouteChangeTracker from "@/RouteChangeTracker";
import { initializeSentryUser } from "@/Sentry";

const Layout = () => {
  RouteChangeTracker();
  const location = useLocation();

  useEffect(() => {
    initializeSentryUser();
  }, []);

  const isIntroPage = location.pathname === "/intro";

  return (
    <>
      {isIntroPage ? (
        <SentryTotalBoundary>
          <Outlet />
        </SentryTotalBoundary>
      ) : (
        <>
          <Header />
          <S.ContentContainer>
            <SentryTotalBoundary>
              <Outlet />
            </SentryTotalBoundary>
          </S.ContentContainer>
        </>
      )}
    </>
  );
};

export default Layout;
