//...
import * as Sentry from "@sentry/react";

Sentry.init({
  dsn: "https://57dffab3064cac2031766ff018d19eae@o4507728730193920.ingest.us.sentry.io/4507728752082944",
  integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
  // Performance Monitoring
  tracesSampleRate: 1.0, //  Capture 100% of the transactions
  // Set 'tracePropagationTargets' to control for which URLs distributed tracing should be enabled
  tracePropagationTargets: ["localhost", /^https:\/\/yourserver\.io\/api/],
  // Session Replay
  replaysSessionSampleRate: 0.1, // This sets the sample rate at 10%. You may want to change it to 100% while in development and then sample at a lower rate in production.
  replaysOnErrorSampleRate: 1.0, // If you're not already sampling the entire session, change the sample rate to 100% when sampling sessions where errors occur.
});

export default Sentry;
