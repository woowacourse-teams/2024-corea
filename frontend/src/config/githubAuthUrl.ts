import { hostType } from "@/utils/hostType";

const getClientId = () => {
  if (hostType === "production") {
    return "Ov23liFoUtO89lG70w9I";
  }
  if (hostType === "release") {
    return "Ov23li3kzpRDuPpAZWRu";
  }
  return "Ov23li3kzpRDuPpAZWRu";
};

const clientId = getClientId();
export const githubAuthUrl = `https://github.com/login/oauth/authorize?client_id=${clientId}`;
