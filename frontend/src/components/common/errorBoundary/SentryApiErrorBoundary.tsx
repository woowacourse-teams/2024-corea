import { ErrorBoundary, captureException } from "@sentry/react";
import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import { ReactNode } from "react";
import Button from "@/components/common/button/Button";
import { HTTPError } from "@/utils/Errors";

const ApiErrorFallback = ({ resetError }: { resetError: () => void }) => {
  return (
    <div>
      <h2>네트워크 에러가 발생했어요</h2>
      <Button onClick={resetError}>다시 시도하기</Button>
    </div>
  );
};

const SentryApiErrorBoundary = ({ children }: { children: ReactNode }) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      onReset={reset}
      fallback={({ resetError }) => <ApiErrorFallback resetError={resetError} />}
      onError={(error) => {
        if (error instanceof HTTPError) {
          captureException(error);
        } else {
          throw error;
        }
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default SentryApiErrorBoundary;
