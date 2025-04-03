import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import type { ReactElement, ReactNode } from "react";
import { ErrorBoundary } from "react-error-boundary";
import { ERROR_STRATEGY } from "@/constants/errorStrategy";
import { ApiError } from "@/utils/CustomError";

type FallbackRender = (params: { error: Error; resetError: () => void }) => ReactElement;

const LocalErrorBoundary = ({
  children,
  resetKeys,
  fallback,
}: {
  children: ReactNode;
  resetKeys: string[];
  fallback: FallbackRender;
}) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      resetKeys={resetKeys}
      onReset={reset}
      fallbackRender={({ error, resetErrorBoundary }) => {
        if (error instanceof ApiError && error.strategy === ERROR_STRATEGY.ERROR_BOUNDARY) {
          return fallback({ error, resetError: resetErrorBoundary });
        }
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default LocalErrorBoundary;
