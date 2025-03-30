import useToast from "../hooks/common/useToast";
import { MutationCache, QueryCache, QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React, { useMemo } from "react";
import useErrorHandling from "@/hooks/common/useErrorHandling";

interface QueryProviderProps {
  children: React.ReactNode;
}

const QueryProvider = ({ children }: QueryProviderProps) => {
  const { showToast } = useToast();
  const { notifyError } = useErrorHandling();

  const queryClient = useMemo(
    () =>
      new QueryClient({
        queryCache: new QueryCache({
          onError: (error) => {
            notifyError(error);
            return;
          },
        }),
        mutationCache: new MutationCache({
          onError: (error) => {
            notifyError(error);
            return;
          },
        }),
        defaultOptions: {
          queries: {
            retry: false,
            throwOnError: true,
            networkMode: "online",
          },
          mutations: {
            networkMode: "always",
          },
        },
      }),
    [showToast],
  );

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};

export default QueryProvider;
