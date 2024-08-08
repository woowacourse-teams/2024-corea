import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import DeliveredFeedbackInfo from "@/mocks/mockResponse/deliveredFeedbackInfo.json";
import FeedbackInfo from "@/mocks/mockResponse/feedbackInfo.json";
import ReceivedFeedbackInfo from "@/mocks/mockResponse/receivedFeedbackInfo.json";

const feedbackHandler = [
  http.get(serverUrl + API_ENDPOINTS.RECEIVED_FEEDBACK, () => {
    return HttpResponse.json(ReceivedFeedbackInfo, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.DELIVERED_FEEDBACK, () => {
    return HttpResponse.json(DeliveredFeedbackInfo, { status: 200 });
  }),
  http.post(serverUrl + API_ENDPOINTS.REVIEWEE_FEEDBACK(1), () => {
    return HttpResponse.json(FeedbackInfo, { status: 200 });
  }),
  http.put(serverUrl + API_ENDPOINTS.PUT_REVIEWEE_FEEDBACK(1, 1), () => {
    return HttpResponse.json(FeedbackInfo, { status: 200 });
  }),
];

export default feedbackHandler;
