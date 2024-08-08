declare namespace NodeJS {
  interface ProcessEnv {
    GOOGLE_ANALYTICS: string;
    SENTRY_DSN_KEY: string;
    SENTRY_AUTH_TOKEN: string;
  }
}
