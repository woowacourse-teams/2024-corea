import DelaySuspense from "../common/delaySuspense/DelaySuspense";
import SentryErrorBoundary from "../common/errorBoundary/SentryErrorBoundary";
import Loading from "../common/loading/Loading";
import React, { Suspense, useEffect } from "react";
import { Outlet } from "react-router-dom";
import Header from "@/components/common/header/Header";
import * as S from "@/components/layout/Layout.style";
import RouteChangeTracker from "@/RouteChangeTracker";
import { initializeSentryUser } from "@/Sentry";

const Layout = () => {
  RouteChangeTracker();

  useEffect(() => {
    initializeSentryUser();
  }, []);

  return (
    <SentryErrorBoundary>
      <S.ContentContainer>
        <Header />
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
