import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import RankingInfo from "@/mocks/mockResponse/rankingInfo.json";

const rankingHandler = [
  http.get(serverUrl + API_ENDPOINTS.RANKING, () => {
    return HttpResponse.json(RankingInfo, { status: 200 });
  }),
];

export default rankingHandler;
