import { HttpResponse, delay, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import reviewInfo from "@/mocks/mockResponse/reviewInfo.json";
import roomInfo from "@/mocks/mockResponse/roomInfo.json";
import roomInfos from "@/mocks/mockResponse/roomInfos.json";

const roomHandler = [
  http.get(serverUrl + API_ENDPOINTS.PARTICIPATED_ROOMS, () => {
    return HttpResponse.json(roomInfos, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.PROGRESS_ROOMS, () => {
    return HttpResponse.json(roomInfos, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.OPENED_ROOMS, async () => {
    await delay(3000);
    return HttpResponse.json(roomInfos, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.CLOSED_ROOMS, () => {
    return HttpResponse.json(roomInfos, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.ROOMS + "/:id", () => {
    return HttpResponse.json(roomInfo, { status: 200 });
  }),
  http.post(serverUrl + API_ENDPOINTS.PARTICIPATE_IN(1), () => {
    return HttpResponse.json(null, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.REVIEWERS(1), () => {
    return HttpResponse.json(reviewInfo, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.REVIEWEES(1), () => {
    return HttpResponse.json(reviewInfo, { status: 200 });
  }),
  http.post(serverUrl + API_ENDPOINTS.REVIEW_COMPLETE, () => {
    return HttpResponse.json(null, { status: 200 });
  }),
];

export default roomHandler;
