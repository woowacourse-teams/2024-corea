import Toast from "./components/common/Toast/Toast";
import ErrorManager from "./components/errorManager/ErrorManager";
import { ErrorModalProvider } from "./providers/ErrorModalProvider";
import { ErrorProvider } from "./providers/ErrorProvider";
import QueryProvider from "./providers/QueryProvider";
import { ToastProvider } from "./providers/ToastProvider";
import router from "./router/router";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import { ThemeProvider } from "styled-components";
import GlobalStyles from "@/styles/globalStyles";
import { theme } from "@/styles/theme";

const enableMocking = async () => {
  if (process.env.NODE_ENV !== "development") {
    return;
  }
  const { worker } = await import("./mocks/browser");
  return worker.start();
};

enableMocking().then(() => {
  ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
    <React.StrictMode>
      <ThemeProvider theme={theme}>
        <ErrorProvider>
          <QueryProvider>
            <ToastProvider>
              <ErrorModalProvider>
                <ErrorManager>
                  <GlobalStyles />
                  <ReactQueryDevtools initialIsOpen={false} />
                  <Toast />
                  <RouterProvider router={router} />
                </ErrorManager>
              </ErrorModalProvider>
            </ToastProvider>
          </QueryProvider>
        </ErrorProvider>
      </ThemeProvider>
    </React.StrictMode>,
  );
});
