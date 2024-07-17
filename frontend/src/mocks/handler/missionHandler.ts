import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";

const reviewerInfo = [
  {
    email: "reviewer1@email.com",
  },
  {
    email: "reviewer2@email.com",
  },
  {
    email: "reviewer3@email.com",
  },
];

const missionHandler = [
  http.get(API_ENDPOINTS.REVIEWER, () => {
    return HttpResponse.json({ reviewerInfo }, { status: 200 });
  }),
];

export default missionHandler;
