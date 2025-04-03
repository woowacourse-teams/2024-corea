import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import ProfileInfo from "@/mocks/mockResponse/profileInfo.json";

const profileHandler = [
  http.get(serverUrl + API_ENDPOINTS.PROFILE, () => {
    return HttpResponse.json(ProfileInfo, { status: 200 });
  }),
  http.get(serverUrl + API_ENDPOINTS.USER_PROFILE("username"), () => {
    return HttpResponse.json(ProfileInfo, { status: 200 });
  }),
];

export default profileHandler;
