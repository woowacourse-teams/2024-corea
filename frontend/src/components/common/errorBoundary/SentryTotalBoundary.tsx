import { ReactNode, Suspense } from "react";
import { useLocation } from "react-router-dom";
import Button from "@/components/common/button/Button";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import SentryApiErrorBoundary from "@/components/common/errorBoundary/SentryApiErrorBoundary";
import Loading from "@/components/common/loading/Loading";
import { Sentry } from "@/Sentry";

const FallbackComponent = ({ resetError }: { resetError: () => void }) => {
  return (
    <div>
      <h2>일시적인 에러가 발생했어요</h2>
      <Button onClick={resetError}>다시 시도하기</Button>
    </div>
  );
};

const SentryTotalBoundary = ({ children }: { children: ReactNode }) => {
  const location = useLocation();

  return (
    <Sentry.ErrorBoundary
      key={location.pathname}
      fallback={({ resetError }) => <FallbackComponent resetError={resetError} />}
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
