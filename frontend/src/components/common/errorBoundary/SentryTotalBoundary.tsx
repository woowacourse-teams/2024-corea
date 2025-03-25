import ErrorBoundarySwitch from "./ErrorBoundarySwitch";
import * as Sentry from "@sentry/react";
import { ReactNode, Suspense } from "react";
import { useLocation } from "react-router-dom";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import Loading from "@/components/common/loading/Loading";
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
      <Suspense
        fallback={
          <DelaySuspense>
            <Loading />
          </DelaySuspense>
        }
      >
        {children}
      </Suspense>
    </Sentry.ErrorBoundary>
  );
};

export default SentryErrorBoundary;
