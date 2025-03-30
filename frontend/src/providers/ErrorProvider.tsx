import type { PropsWithChildren } from "react";
import { createContext, useState } from "react";

export const ErrorContext = createContext<Error | null>(null);
export const ErrorDispatchContext = createContext<(error: Error) => void>(() => {});

export const ErrorProvider = ({ children }: PropsWithChildren) => {
  const [error, setError] = useState<Error | null>(null);

  return (
    <ErrorContext.Provider value={error}>
      <ErrorDispatchContext.Provider value={setError}>{children}</ErrorDispatchContext.Provider>
    </ErrorContext.Provider>
  );
};
