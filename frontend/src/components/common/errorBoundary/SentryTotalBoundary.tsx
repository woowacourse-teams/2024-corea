import Fallback from "./Fallback";
import { ReactNode, Suspense } from "react";
import { useLocation } from "react-router-dom";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import SentryApiErrorBoundary from "@/components/common/errorBoundary/SentryApiErrorBoundary";
import Loading from "@/components/common/loading/Loading";
import { Sentry } from "@/Sentry";
import MESSAGES from "@/constants/message";

const SentryTotalBoundary = ({ children }: { children: ReactNode }) => {
  const { pathname } = useLocation();

  return (
    <Sentry.ErrorBoundary
      key={pathname}
      fallback={({ resetError }) => (
        <Fallback message={MESSAGES.ERROR.BOUNDARY_TOTAL} resetError={resetError} />
      )}
      onError={(error) => {
        Sentry.withScope((scope) => {
          scope.setTag("type", "runtimeError");

          Sentry.captureException(error);
        });
      }}
    >
      <SentryApiErrorBoundary>
        <Suspense
          fallback={
            <DelaySuspense>
              <Loading />
            </DelaySuspense>
          }
        >
          {children}
        </Suspense>
      </SentryApiErrorBoundary>
    </Sentry.ErrorBoundary>
  );
};

export default SentryTotalBoundary;
