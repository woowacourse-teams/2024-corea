import { ErrorBoundary } from "@sentry/react";
import { ReactNode, Suspense } from "react";
import Button from "@/components/common/button/Button";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import SentryApiErrorBoundary from "@/components/common/errorBoundary/SentryApiErrorBoundary";
import Loading from "@/components/common/loading/Loading";

const FallbackComponent = ({ resetError }: { resetError: () => void }) => {
  return (
    <div>
      <h2>일시적인 에러가 발생했어요</h2>
      <Button onClick={resetError}>다시 시도하기</Button>
    </div>
  );
};

const SentryTotalBoundary = ({ children }: { children: ReactNode }) => {
  return (
    <ErrorBoundary fallback={({ resetError }) => <FallbackComponent resetError={resetError} />}>
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
    </ErrorBoundary>
  );
};

export default SentryTotalBoundary;
