import ErrorBoundarySwitch from "./ErrorBoundarySwitch";
import * as Sentry from "@sentry/react";
import { ReactNode } from "react";
import { useLocation } from "react-router-dom";
import logErrorToSentry from "@/utils/logSentryError";

const SentryErrorBoundary = ({ children }: { children: ReactNode }) => {
  const { pathname } = useLocation();

  return (
    <Sentry.ErrorBoundary
      key={pathname}
      fallback={({ error, resetError }) => (
        <ErrorBoundarySwitch error={error as Error} resetError={resetError} />
      )}
      onError={(error) => logErrorToSentry(error as Error)}
    >
      {children}
    </Sentry.ErrorBoundary>
  );
};

export default SentryErrorBoundary;
