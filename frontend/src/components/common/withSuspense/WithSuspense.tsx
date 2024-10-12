import { FC, ReactNode, Suspense } from "react";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";

const WithSuspense = <T extends object>(Component: FC<T>, fallback: ReactNode) => {
  const WrappedComponent = (props: T): ReactNode => (
    <Suspense fallback={<DelaySuspense>{fallback}</DelaySuspense>}>
      <Component {...props} />
    </Suspense>
  );

  WrappedComponent.displayName = "WithSuspense";

  return WrappedComponent;
};

export default WithSuspense;
