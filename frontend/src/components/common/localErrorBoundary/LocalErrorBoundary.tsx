import { ErrorBoundary } from "@sentry/react";
import type { ReactElement, ReactNode } from "react";
import { ApiError } from "@/utils/Errors";

type FallbackRender = (params: { error: Error; resetError: () => void }) => ReactElement;

const LocalErrorBoundary = ({
  children,
  fallback,
}: {
  children: ReactNode;
  fallback: FallbackRender;
}) => {
  return (
    <ErrorBoundary
      fallback={({ error, resetError }) => {
        if (error instanceof ApiError) {
          return fallback({ error, resetError });
        }
        throw error;
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default LocalErrorBoundary;
