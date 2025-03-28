import ApiFallback from "./ApiFallback";
import AuthorizationFallback from "./AuthorizationFallback";
import DefaultFallback from "./DefaultFallback";
import NetworkFallback from "./NetworkFallback";
import { useQueryClient } from "@tanstack/react-query";
import useNetwork from "@/hooks/common/useNetwork";
import { ApiError, AuthorizationError, NetworkError } from "@/utils/Errors";

interface ErrorBoundarySwitchProps {
  error: Error;
  resetError: () => void;
}

const ErrorBoundarySwitch = ({ error, resetError }: ErrorBoundarySwitchProps) => {
  const isOnline = useNetwork();
  const queryClient = useQueryClient();

  const handleRetry = () => {
    resetError();
    queryClient.invalidateQueries();
  };

  if (!isOnline || error.name === "ChunkLoadError" || error instanceof NetworkError) {
    return <NetworkFallback onRetry={handleRetry} />;
  }

  switch (true) {
    case error.name === "ChunkLoadError":
      return <NetworkFallback onRetry={handleRetry} />;
    case error instanceof NetworkError:
      return <NetworkFallback onRetry={handleRetry} />;
    case error instanceof AuthorizationError:
      return <AuthorizationFallback />;
    case error instanceof ApiError:
      return <ApiFallback onRetry={handleRetry} errorMessage={error.message} />;
    default:
      return <DefaultFallback onRetry={handleRetry} />;
  }
};

export default ErrorBoundarySwitch;
