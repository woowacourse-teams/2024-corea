import Fallback from "./Fallback";
import { useQueryErrorResetBoundary } from "@tanstack/react-query";
import { ReactNode } from "react";
import { useLocation } from "react-router-dom";
import { Sentry } from "@/Sentry";
import MESSAGES from "@/constants/message";
import { ApiError } from "@/utils/Errors";

const SentryApiErrorBoundary = ({ children }: { children: ReactNode }) => {
  const { reset } = useQueryErrorResetBoundary();
  const { pathname } = useLocation();

  return (
    <Sentry.ErrorBoundary
      key={pathname}
      onReset={reset}
      fallback={({ resetError }) => (
        <Fallback message={MESSAGES.ERROR.BOUNDARY_TOTAL} resetError={resetError} />
      )}
      onError={(error) => {
        if (error instanceof ApiError) {
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
