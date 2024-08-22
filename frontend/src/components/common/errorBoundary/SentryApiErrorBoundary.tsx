import Fallback from "./Fallback";
import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import { ReactNode } from "react";
import Button from "@/components/common/button/Button";
import { Sentry } from "@/Sentry";
import MESSAGES from "@/constants/message";
import { HTTPError } from "@/utils/Errors";

const SentryApiErrorBoundary = ({ children }: { children: ReactNode }) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <Sentry.ErrorBoundary
      onReset={reset}
      fallback={({ resetError }) => (
        <Fallback message={MESSAGES.ERROR.BOUNDARY_TOTAL} resetError={resetError} />
      )}
      onError={(error) => {
        if (error instanceof HTTPError) {
          Sentry.withScope((scope) => {
            scope.setTag("type", "apiError");

            Sentry.captureException(error);
          });
        } else {
          throw error;
        }
      }}
    >
      {children}
    </Sentry.ErrorBoundary>
  );
};

export default SentryApiErrorBoundary;
