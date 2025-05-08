import { ProfileData } from "@/@types/profile";
import apiClient from "@/apis/apiClient";
import { API_ENDPOINTS } from "@/apis/endpoints";
import MESSAGES from "@/constants/message";

export const getProfile = async (): Promise<ProfileData> => {
  const { data } = await apiClient.get({
    endpoint: API_ENDPOINTS.PROFILE,
    errorMessage: MESSAGES.ERROR.GET_PROFILE,
  });

  return data;
};

export const getUserProfile = async (userName: string): Promise<ProfileData> => {
  const { data } = await apiClient.get({
    endpoint: API_ENDPOINTS.USER_PROFILE(userName),
    errorMessage: MESSAGES.ERROR.GET_PROFILE,
  });

  return data;
};
