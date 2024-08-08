export const serverUrl =
  process.env.NODE_ENV === "production"
    ? "https://1137-58-143-138-249.ngrok-free.app"
    : "http://localhost:8080";
