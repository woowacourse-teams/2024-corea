import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";

export const postLogin = async (code: string): Promise<any> => {
  const response = await fetch(`${serverUrl}${API_ENDPOINTS.LOGIN}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ code }),
    credentials: "include",
  });

  if (!response.ok) {
    const errorResponse = await response.json();
    throw new Error("Invalid token. Please log in again.");
  }

  const text = await response.text();

  const authHeader = response.headers.get("Authorization");
  const accessToken = authHeader?.split(" ")[1];

  const authBody = text ? JSON.parse(text) : response;
  const refreshToken = authBody.refreshToken;
  const userInfo = authBody.userInfo;

  if (!accessToken) {
    throw new Error("Authorization header is missing.");
  }

  return { accessToken, refreshToken, userInfo };
};

export const postLogout = async (): Promise<void> => {
  const res = await apiClient.post({
    endpoint: API_ENDPOINTS.LOGOUT,
    // errorMessage: MESSAGES.ERROR.GET_MY_REVIEWEES,
  });
};
