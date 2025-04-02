import DefaultFallback from "./DefaultFallback";
import NetworkFallback from "./NetworkFallback";
import { useQueryClient } from "@tanstack/react-query";
import useNetwork from "@/hooks/common/useNetwork";
import { NetworkError } from "@/utils/CustomError";

interface ErrorBoundarySwitchProps {
  error: Error;
  resetError: () => void;
}

const ErrorBoundarySwitch = ({ error, resetError }: ErrorBoundarySwitchProps) => {
  const isOnline = useNetwork();
  const queryClient = useQueryClient();

  const handleRetry = () => {
    resetError();
    queryClient.invalidateQueries({ predicate: (query) => query.state.status === "error" });
  };

  switch (true) {
    case !isOnline || error.name === "ChunkLoadError":
    case error instanceof NetworkError:
      return <NetworkFallback onRetry={handleRetry} />;
    default:
      return <DefaultFallback onRetry={handleRetry} />;
  }
};

export default ErrorBoundarySwitch;
