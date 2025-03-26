import { type ReactElement, ReactNode, Suspense } from "react";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";

interface LocalSuspenseProps {
  children: ReactNode;
  fallback: ReactElement | null;
}

const LocalSuspense = ({ children, fallback }: LocalSuspenseProps) => {
  if (fallback === null) {
    return <Suspense fallback={null}>{children}</Suspense>;
  }

  return <Suspense fallback={<DelaySuspense>{fallback}</DelaySuspense>}>{children}</Suspense>;
};

export default LocalSuspense;
