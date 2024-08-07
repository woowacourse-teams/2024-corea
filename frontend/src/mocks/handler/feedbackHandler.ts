import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import FeedbackInfo from "@/mocks/mockResponse/feedbackInfo.json";

const feedbackHandler = [
  http.get(serverUrl + API_ENDPOINTS.REVIEWEE_FEEDBACK(1), () => {
    return HttpResponse.json(FeedbackInfo, { status: 200 });
  }),
  http.post(serverUrl + API_ENDPOINTS.REVIEWEE_FEEDBACK(1), () => {
    return HttpResponse.json(FeedbackInfo, { status: 200 });
  }),
];

export default feedbackHandler;
