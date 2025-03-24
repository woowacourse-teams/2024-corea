import ApiFallback from "./ApiFallback";
import AuthorizationFallback from "./AuthorizationFallback";
import DefaultFallback from "./DefaultFallback";
import { ApiError, AuthorizationError } from "@/utils/Errors";

interface FallbackProps {
  error: Error;
  resetError: () => void;
}

const Fallback = ({ error, resetError }: FallbackProps) => {
  switch (true) {
    case error instanceof AuthorizationError:
      return <AuthorizationFallback />;
    case error instanceof ApiError:
      return <ApiFallback onRetry={resetError} />;
    default:
      return <DefaultFallback onRetry={resetError} />;
  }
};

export default Fallback;
