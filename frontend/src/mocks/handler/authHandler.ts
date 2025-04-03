import { HttpResponse, http } from "msw";
import { API_ENDPOINTS } from "@/apis/endpoints";
import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";
import authMockData from "@/mocks/mockResponse/authInfo.json";

const { mockAccessToken, mockRefreshToken, loginSuccessResponse } = authMockData;

interface LoginRequestBody {
  code: string;
}

const authHandler = [
  http.post(serverUrl + API_ENDPOINTS.LOGIN, async ({ request }) => {
    const body = (await request.json()) as LoginRequestBody;

    if (!body.code) {
      return new HttpResponse(JSON.stringify({ message: MESSAGES.ERROR.POST_LOGIN }), {
        status: 400,
      });
    }

    return HttpResponse.json(loginSuccessResponse, {
      status: 200,
      headers: {
        "Content-Type": "application/json",
        Authorization: mockAccessToken,
        "Set-Cookie": `refreshToken=${mockRefreshToken}`,
      },
    });
  }),

  http.post(serverUrl + API_ENDPOINTS.REFRESH, async ({ cookies }) => {
    const refreshToken = cookies.refreshToken;

    if (!refreshToken || refreshToken !== mockRefreshToken) {
      return new HttpResponse(
        JSON.stringify({
          exceptionType: "TOKEN_EXPIRED",
          message: MESSAGES.ERROR.POST_REFRESH,
        }),
        { status: 401 },
      );
    }

    return new HttpResponse(
      JSON.stringify({
        accessToken: mockAccessToken,
      }),
      {
        status: 200,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${mockAccessToken}`,
        },
      },
    );
  }),

  http.post(serverUrl + API_ENDPOINTS.LOGOUT, async () => {
    return new HttpResponse(JSON.stringify({ message: "로그아웃 성공" }), {
      status: 200,
      headers: {
        "Set-Cookie": `refreshToken=; Max-Age=0; Path=/;`,
      },
    });
  }),
];

export default authHandler;
