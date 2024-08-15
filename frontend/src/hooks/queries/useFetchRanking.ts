import { useQuery } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { getRankingList } from "@/apis/ranking.api";

export const useFetchRanking = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.RANKING],
    queryFn: getRankingList,
    networkMode: "always",
    retry: false,
  });
};
