import { hostType } from "@/utils/hostType";

const getServerUrl = () => {
  if (hostType === "production") {
    return "https://api-prod.code-review-area.com";
  }
  if (hostType === "release") {
    return "https://api.code-review-area.com";
  }
  // return "http://localhost:8080";
  return "https://api.code-review-area.com";
};

export const serverUrl = getServerUrl();
