import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import alarmcount from "@/mocks/mockResponse/alarmCount.json";
import alarmInfos from "@/mocks/mockResponse/alarmInfos.json";

const alarmHandler = [
  http.get(serverUrl + API_ENDPOINTS.ALARM_COUNT, () => {
    return HttpResponse.json(alarmcount, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.ALARM_LISt, () => {
    return HttpResponse.json(alarmInfos, { status: 200 });
  }),
];

export default alarmHandler;
