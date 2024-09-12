import { useSuspenseQuery } from "@tanstack/react-query";
import { getUserProfile } from "@/apis/profile.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchProfile = () => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.PROFILE],
    queryFn: getUserProfile,
    retry: false,
  });
};
