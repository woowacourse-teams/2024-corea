import { useQuery } from "@tanstack/react-query";
import { getUserProfile } from "@/apis/profile.api";
import QUERY_KEYS from "@/apis/queryKeys";

const useFetchProfile = () => {
  return useQuery({ queryKey: [QUERY_KEYS.PROFILE], queryFn: getUserProfile });
};

export default useFetchProfile;
