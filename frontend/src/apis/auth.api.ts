import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { UserInfo } from "@/@types/userInfo";
import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";

export const postLogin = async (
  code: string,
): Promise<{ accessToken: string; refreshToken: string; userInfo: UserInfo }> => {
  const response = await fetch(`${serverUrl}${API_ENDPOINTS.LOGIN}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ code }),
  });

  if (!response.ok) {
    throw new Error(MESSAGES.ERROR.POST_LOGIN);
  }

  const text = await response.text();

  const accessToken = response.headers.get("Authorization");

  const authBody = text ? JSON.parse(text) : response;
  const refreshToken = authBody.refreshToken;
  const userInfo = authBody.userInfo as UserInfo;

  if (!accessToken) {
    throw new Error(MESSAGES.ERROR.POST_LOGIN);
  }

  return { accessToken, refreshToken, userInfo };
};

export const postLogout = async (): Promise<void> => {
  await apiClient.post({
    endpoint: API_ENDPOINTS.LOGOUT,
    errorMessage: MESSAGES.ERROR.POST_LOGOUT,
  });
};
