import DelaySuspense from "../common/delaySuspense/DelaySuspense";
import SentryErrorBoundary from "../common/errorBoundary/SentryErrorBoundary";
import Loading from "../common/loading/Loading";
import React, { Suspense, useEffect } from "react";
import { Outlet, useLocation } from "react-router-dom";
import Header from "@/components/common/header/Header";
import * as S from "@/components/layout/Layout.style";
import RouteChangeTracker from "@/RouteChangeTracker";
import { initializeSentryUser } from "@/Sentry";

const Layout = () => {
  RouteChangeTracker();

  useEffect(() => {
    initializeSentryUser();
  }, []);

  const { pathname } = useLocation();
  const isNoShadowPage = ["/", "/intro"].includes(pathname);

  return (
    <SentryErrorBoundary>
      <S.ContentContainer>
        <Header showShadow={!isNoShadowPage} shouldFixed={pathname === "/intro"} />
        <S.ContentSection>
          <Suspense
            fallback={
              <DelaySuspense>
                <Loading />
              </DelaySuspense>
            }
          >
            <Outlet />
          </Suspense>
        </S.ContentSection>
      </S.ContentContainer>
    </SentryErrorBoundary>
  );
};

export default Layout;
