import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { UserInfoResponse } from "@/hooks/mutations/useMutateAuth";
import { UserInfo } from "@/@types/userInfo";
import MESSAGES from "@/constants/message";
import { AuthorizationError } from "@/utils/CustomError";

export const postLogin = async (code: string): Promise<UserInfoResponse> => {
  const { data, headers } = await apiClient.post({
    endpoint: API_ENDPOINTS.LOGIN,
    body: { code },
    errorMessage: MESSAGES.ERROR.POST_LOGIN,
  });

  const accessToken = headers.get("Authorization");
  const userInfo = data.userInfo as UserInfo;
  const memberRole = data.memberRole as string;

  if (!accessToken || !userInfo || !memberRole) {
    throw new AuthorizationError(MESSAGES.ERROR.POST_LOGIN);
  }

  return { accessToken, userInfo, memberRole };
};

export const postLogout = async (): Promise<void> => {
  await apiClient.post({
    endpoint: API_ENDPOINTS.LOGOUT,
    errorMessage: MESSAGES.ERROR.POST_LOGOUT,
  });
};
