import { hostType } from "@/utils/hostType";

const getServerUrl = () => {
  if (hostType === "production") {
    return "https://api-prod.code-review-area.com";
  }
  if (hostType === "release") {
    return "https://api.code-review-area.com";
  }
  return "http://192.168.1.48:8080";
};

export const serverUrl = getServerUrl();
