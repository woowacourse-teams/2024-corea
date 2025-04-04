import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import type { ReactElement, ReactNode } from "react";
import { ErrorBoundary } from "react-error-boundary";
import { ApiError } from "@/utils/CustomError";

type FallbackRender = (params: { error: Error; resetError: () => void }) => ReactElement;

const shouldThrowToGlobal = (error: Error): boolean => {
  if (error instanceof ApiError) {
    const criticalStatusList = [403, 404, 500, 503];
    return criticalStatusList.includes(error.status ?? -1);
  }
  return true; // 일반 Error도 전역 처리
};

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
        if (shouldThrowToGlobal(error)) {
          throw error; // 전역으로 넘김 (SentryErrorBoundary에서 Sentry 로깅)
        }

        return fallback({ error, resetError: resetErrorBoundary });
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default LocalErrorBoundary;
