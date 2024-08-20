import Fallback from "./Fallback";
import { ReactNode, Suspense } from "react";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import SentryApiErrorBoundary from "@/components/common/errorBoundary/SentryApiErrorBoundary";
import Loading from "@/components/common/loading/Loading";
import { Sentry } from "@/Sentry";
import MESSAGES from "@/constants/message";

const SentryTotalBoundary = ({ children }: { children: ReactNode }) => {
  return (
    <Sentry.ErrorBoundary
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
