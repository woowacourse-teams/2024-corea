import Fallback from "./Fallback";
import { ReactNode, Suspense } from "react";
import Button from "@/components/common/button/Button";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import SentryApiErrorBoundary from "@/components/common/errorBoundary/SentryApiErrorBoundary";
import Loading from "@/components/common/loading/Loading";
import { BannerContainer } from "@/components/main/banner/Banner.style";
import { Sentry } from "@/Sentry";
import MESSAGES from "@/constants/message";

const SentryTotalBoundary = ({ children }: { children: ReactNode }) => {
  const location = useLocation();

  return (
    <Sentry.ErrorBoundary
      key={location.pathname}
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
