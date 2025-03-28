import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import type { ReactElement, ReactNode } from "react";
import { ErrorBoundary } from "react-error-boundary";
import { ApiError } from "@/utils/Errors";

type FallbackRender = (params: { error: Error; resetError: () => void }) => ReactElement;

const LocalErrorBoundary = ({
  children,
  fallback,
}: {
  children: ReactNode;
  resetKeys?: string;
  fallback: FallbackRender;
}) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      onReset={reset}
      fallbackRender={({ error, resetErrorBoundary }) => {
        if (error instanceof ApiError) {
          return fallback({ error, resetError: resetErrorBoundary });
        }
        throw error;
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default LocalErrorBoundary;
