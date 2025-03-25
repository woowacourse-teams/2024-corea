import { ErrorBoundary } from "@sentry/react";
import type { ReactElement, ReactNode } from "react";
import { ApiError } from "@/utils/Errors";

const LocalErrorBoundary = ({
  children,
  fallback,
}: {
  children: ReactNode;
  fallback: ReactElement;
}) => {
  return (
    <ErrorBoundary
      fallback={({ error }) => {
        if (error instanceof ApiError) return fallback;
        throw error;
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default LocalErrorBoundary;
