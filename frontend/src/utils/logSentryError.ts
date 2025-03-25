import * as Sentry from "@sentry/react";
import { ApiError, AuthorizationError } from "@/utils/Errors";

const logErrorToSentry = (error: Error) => {
  Sentry.withScope((scope) => {
    scope.setTag("environment", process.env.NODE_ENV);
    scope.setExtra("url", window.location.href);

    if (error instanceof ApiError) {
      scope.setTag("type", "apiError");
      scope.setContext("apiError", {
        message: error.message,
        name: error.name,
      });
    } else if (error instanceof AuthorizationError) {
      scope.setTag("type", "authorizationError");
      scope.setContext("authorizationError", {
        message: error.message,
        name: error.name,
      });
    } else {
      scope.setTag("type", "runtimeError");
      scope.setContext("runtimeError", {
        name: error.name,
        message: error.message,
        stack: error.stack,
      });
    }

    Sentry.captureException(error);
  });
};

export default logErrorToSentry;
