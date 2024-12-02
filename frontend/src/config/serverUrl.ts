import { hostType } from "@/utils/hostType";

const getServerUrl = () => {
  if (hostType === "production") {
    return "https://api-prod.code-review-area.com";
  }
  if (hostType === "release") {
    return "https://prod.code-review-area.com";
  }
  return "http://localhost:8080";
};

export const serverUrl = getServerUrl();
