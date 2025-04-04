import ApiFallback from "./ApiFallback";
import DefaultFallback from "./DefaultFallback";
import NetworkFallback from "./NetworkFallback";
import { useQueryClient } from "@tanstack/react-query";
import useNetwork from "@/hooks/common/useNetwork";
import { ApiError, NetworkError } from "@/utils/CustomError";

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
    case error instanceof ApiError:
      return <ApiFallback onRetry={handleRetry} errorMessage={error.message} />;
    default:
      return <DefaultFallback onRetry={handleRetry} />;
  }
};

export default ErrorBoundarySwitch;
