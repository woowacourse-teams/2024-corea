import SentryTotalBoundary from "./components/common/errorBoundary/SentryTotalBoundary";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import React from "react";
import { Outlet } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import Toast from "@/components/common/Toast/Toast";
import { ToastProvider } from "@/providers/ToastProvider";
import GlobalStyles from "@/styles/globalStyles";
import { theme } from "@/styles/theme";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      networkMode: "always",
      refetchOnWindowFocus: false,
      retry: false,
      throwOnError: true,
    },
    mutations: {
      networkMode: "always",
      onError: () => console.log("에러"),
    },
  },
});

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <ToastProvider>
          <GlobalStyles />
          <Toast />
          <SentryTotalBoundary>
            <Outlet />
          </SentryTotalBoundary>
        </ToastProvider>
      </ThemeProvider>
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
};

export default App;
