import React, { useEffect } from "react";
import * as S from "@/components/layout/Layout.style";
import RouteChangeTracker from "@/RouteChangeTracker";
import { initializeSentryUser } from "@/Sentry";

const Layout = ({ children }: { children: React.ReactNode }) => {
  RouteChangeTracker();

  useEffect(() => {
    initializeSentryUser();
  }, []);

  return <S.ContentContainer>{children}</S.ContentContainer>;
};

export default Layout;
