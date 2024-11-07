import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import alarmInfos from "@/mocks/mockResponse/alarmInfos.json";

const alarmHandler = [
  http.get(serverUrl + API_ENDPOINTS.ALARM_LISt, () => {
    return HttpResponse.json(alarmInfos, { status: 200 });
  }),
];

export default alarmHandler;
