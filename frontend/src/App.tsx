import QueryProvider from "./QueryProvider";
import Toast from "./components/common/Toast/Toast";
import SentryTotalBoundary from "./components/common/errorBoundary/SentryTotalBoundary";
import { ToastProvider } from "./providers/ToastProvider";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import React from "react";
import { Outlet } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import GlobalStyles from "@/styles/globalStyles";
import { theme } from "@/styles/theme";

const App = () => {
  return (
    <QueryProvider>
      <ToastProvider>
        <GlobalStyles />
        <ThemeProvider theme={theme}>
          <SentryTotalBoundary>
            <ReactQueryDevtools initialIsOpen={false} />
            <Toast />
            <Outlet />
          </SentryTotalBoundary>
        </ThemeProvider>
      </ToastProvider>
    </QueryProvider>
  );
};

export default App;
