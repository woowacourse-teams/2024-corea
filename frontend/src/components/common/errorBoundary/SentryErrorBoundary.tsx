import ErrorBoundarySwitch from "./ErrorBoundarySwitch";
import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import { ReactNode } from "react";
import { ErrorBoundary } from "react-error-boundary";
import { useLocation } from "react-router-dom";
import useNetwork from "@/hooks/common/useNetwork";
import logErrorToSentry from "@/utils/logSentryError";

const SentryErrorBoundary = ({ children }: { children: ReactNode }) => {
  const isOnline = useNetwork();
  const { pathname } = useLocation();
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      resetKeys={[isOnline, pathname]}
      onReset={reset}
      fallbackRender={({ error, resetErrorBoundary }) => (
        <ErrorBoundarySwitch error={error as Error} resetError={resetErrorBoundary} />
      )}
      onError={logErrorToSentry}
    >
      {children}
    </ErrorBoundary>
  );
};

export default SentryErrorBoundary;
