//...
import { hostType } from "./utils/hostType";
import * as Sentry from "@sentry/react";
import SECRET_KEYS from "@/config/secretKeys";

const isProd = hostType === "production";

Sentry.init({
  dsn: SECRET_KEYS.sentryDsnKey,
  environment: hostType,
  integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
  tracesSampleRate: isProd ? 0.2 : 1.0,
  tracePropagationTargets: ["localhost", /^https:\/\/(www\.)?code-review-area\.com/],
  replaysSessionSampleRate: isProd ? 0.1 : 1.0,
  replaysOnErrorSampleRate: 1.0,
});

const initializeSentryUser = () => {
  const userInfo = localStorage.getItem("userInfo");
  if (userInfo) {
    const parsed = JSON.parse(userInfo);
    Sentry.setUser({
      id: parsed.id,
      username: parsed.name,
      email: parsed.login,
    });
  }
};

export { Sentry, initializeSentryUser };
