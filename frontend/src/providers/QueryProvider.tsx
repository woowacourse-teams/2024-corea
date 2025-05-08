import { MutationCache, QueryCache, QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React, { useMemo } from "react";
import useErrorStore from "@/hooks/common/useErrorStore";

interface QueryProviderProps {
  children: React.ReactNode;
}

const QueryProvider = ({ children }: QueryProviderProps) => {
  const { setError } = useErrorStore();

  const queryClient = useMemo(
    () =>
      new QueryClient({
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
        queryCache: new QueryCache({
          onError: (error) => {
            setError(error);
          },
        }),
        mutationCache: new MutationCache({
          onError: (error) => {
            setError(error);
          },
        }),
      }),
    [],
  );

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
};

export default QueryProvider;
