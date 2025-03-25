import QueryProvider from "./QueryProvider";
import Toast from "./components/common/Toast/Toast";
import DelaySuspense from "./components/common/delaySuspense/DelaySuspense";
import SentryErrorBoundary from "./components/common/errorBoundary/SentryErrorBoundary";
import Header from "./components/common/header/Header";
import Loading from "./components/common/loading/Loading";
import Layout from "./components/layout/Layout";
import { ToastProvider } from "./providers/ToastProvider";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import React, { Suspense } from "react";
import { Outlet } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import GlobalStyles from "@/styles/globalStyles";
import { theme } from "@/styles/theme";

const App = () => {
  return (
    <ToastProvider>
      <QueryProvider>
        <GlobalStyles />
        <ThemeProvider theme={theme}>
          <ReactQueryDevtools initialIsOpen={false} />
          <Toast />
          <Header />
          <SentryErrorBoundary>
            <Suspense
              fallback={
                <DelaySuspense>
                  <Loading />
                </DelaySuspense>
              }
            >
              <Layout>
                <Outlet />
              </Layout>
            </Suspense>
          </SentryErrorBoundary>
        </ThemeProvider>
      </QueryProvider>
    </ToastProvider>
  );
};

export default App;
