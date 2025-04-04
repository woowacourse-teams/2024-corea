import { hostType } from "@/utils/hostType";

const getServerUrl = () => {
  if (hostType === "production") {
    return "https://prod.code-review-area.com";
  }
  if (hostType === "release") {
    return "https://api-dev.code-review-area.com";
  }
  return "http://localhost:8080";
};

export const serverUrl = getServerUrl();
