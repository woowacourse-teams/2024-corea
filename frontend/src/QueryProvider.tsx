import MESSAGES from "./constants/message";
import useToast from "./hooks/common/useToast";
import { NetworkError } from "./utils/Errors";
import { MutationCache, QueryCache, QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React, { useMemo } from "react";

interface QueryProviderProps {
  children: React.ReactNode;
}

const QueryProvider = ({ children }: QueryProviderProps) => {
  const { openToast } = useToast();

  const queryClient = useMemo(
    () =>
      new QueryClient({
        queryCache: new QueryCache({
          onError: (error) => {
            if (error instanceof NetworkError) {
              openToast(MESSAGES.ERROR.OFFLINE, "error");
              return;
            }
          },
        }),
        mutationCache: new MutationCache({
          onError: (error) => {
            if (error instanceof NetworkError) {
              openToast(MESSAGES.ERROR.OFFLINE, "error");
            }
          },
        }),
        defaultOptions: {
          queries: {
            retry: false,
            throwOnError: true,
          },
          mutations: {
            networkMode: "always",
          },
        },
      }),
    [openToast],
  );

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};

export default QueryProvider;
