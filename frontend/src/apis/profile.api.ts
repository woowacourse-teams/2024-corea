import apiClient from "@/apis/apiClient";
import { API_ENDPOINTS } from "@/apis/endpoints";
import MESSAGES from "@/constants/message";

export const getUserProfile = async (): Promise<ProfileData> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.PROFILE,
    errorMessage: MESSAGES.ERROR.GET_PROFILE,
  });

  return res;
};
