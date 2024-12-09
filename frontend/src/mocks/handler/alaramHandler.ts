import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import alarmCount from "@/mocks/mockResponse/alarmCount.json";
import alarmInfos from "@/mocks/mockResponse/alarmInfos.json";

const alarmHandler = [
  http.get(serverUrl + API_ENDPOINTS.ALARM_COUNT, () => {
    return HttpResponse.json(alarmCount, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.ALARM_LIST, () => {
    return HttpResponse.json(alarmInfos, { status: 200 });
  }),
  http.post(serverUrl + API_ENDPOINTS.ALARM_CHECKED, () => {
    return HttpResponse.json({ alarmId: 1, alarmType: "USER" }, { status: 200 });
  }),
];

export default alarmHandler;
