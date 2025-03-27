import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { getProfile, getUserProfile } from "@/apis/profile.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchProfile = () => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.PROFILE],
    queryFn: getProfile,
  });
};

export const useFetchUserProfile = (userName: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.USER_PROFILE, userName],
    queryFn: () => getUserProfile(userName),
    enabled: !!userName,
  });
};
