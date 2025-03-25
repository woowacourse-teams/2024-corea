import { type ReactElement, ReactNode, Suspense } from "react";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";

const LocalSuspense = ({ children, fallback }: { children: ReactNode; fallback: ReactElement }) => {
  return <Suspense fallback={<DelaySuspense>{fallback}</DelaySuspense>}>{children}</Suspense>;
};

export default LocalSuspense;
