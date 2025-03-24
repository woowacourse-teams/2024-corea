import React, { useEffect } from "react";
import Header from "@/components/common/header/Header";
import * as S from "@/components/layout/Layout.style";
import RouteChangeTracker from "@/RouteChangeTracker";
import { initializeSentryUser } from "@/Sentry";

const Layout = ({ children }: { children: React.ReactNode }) => {
  RouteChangeTracker();

  useEffect(() => {
    initializeSentryUser();
  }, []);

  return (
    <S.ContentContainer>
      <Header />
      <S.ContentSection>{children}</S.ContentSection>
    </S.ContentContainer>
  );
};

export default Layout;
