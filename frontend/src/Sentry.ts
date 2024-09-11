//...
import { hostType } from "./utils/hostType";
import * as Sentry from "@sentry/react";
import SECRET_KEYS from "@/config/secretKeys";

Sentry.init({
  dsn: SECRET_KEYS.sentryDsnKey,
  environment: hostType,
  integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
  // Performance Monitoring
  tracesSampleRate: 1.0, //  Capture 100% of the transactions
  // Set 'tracePropagationTargets' to control for which URLs distributed tracing should be enabled
  tracePropagationTargets: ["localhost", /^https:\/\/code-review-area\.com/],
  // Session Replay
  replaysSessionSampleRate: 0.0, // This sets the sample rate at 10%. You may want to change it to 100% while in development and then sample at a lower rate in production.
  replaysOnErrorSampleRate: 0.0, // If you're not already sampling the entire session, change the sample rate to 100% when sampling sessions where errors occur.
});

const initializeSentryUser = () => {
  const userInfo = localStorage.getItem("userInfo");
  if (userInfo) {
    Sentry.setUser(JSON.parse(userInfo));
  }
};

export { Sentry, initializeSentryUser };
