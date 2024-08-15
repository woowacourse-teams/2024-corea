export const serverUrl =
  process.env.NODE_ENV === "production"
    ? "https://api.code-review-area.com"
    : "http://localhost:8080";
