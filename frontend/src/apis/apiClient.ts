import { API_ENDPOINTS } from "./endpoints";
import { Method, QueueItem } from "@/@types/apiClient";
import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";
import { AuthorizationError, HTTPError } from "@/utils/Errors";

export interface ApiProps {
  endpoint: string;
  headers?: Record<string, string>;
  body?: object | null;
  errorMessage?: string;
}

export interface RequestProps extends ApiProps {
  method: Method;
}

let isRefreshing = false;
let failedQueue: QueueItem[] = [];

const processQueue = (error: Error | null = null, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token as string);
    }
  });

  failedQueue = [];
};

const refreshAccessToken = async (): Promise<string | undefined> => {
  const response = await fetch(`${serverUrl}${API_ENDPOINTS.REFRESH}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
  });

  const text = await response.text();
  const data = text ? JSON.parse(text) : null;

  const newAccessToken = response.headers.get("Authorization");

  if (!response.ok) {
    if (response.status === 401 && data.exceptionType === "TOKEN_EXPIRED") {
      const error = new AuthorizationError(data.message || MESSAGES.ERROR.POST_REFRESH);
      processQueue(error, null);
      isRefreshing = false;
      alert("토큰이 만료되었습니다. 다시 로그인 해주세요!");
      localStorage.clear();
      window.location.href = "/";
    } else {
      throw new HTTPError(data.message || MESSAGES.ERROR.POST_REFRESH);
    }
  } else if (newAccessToken) {
    localStorage.setItem("accessToken", newAccessToken);
    processQueue(null, newAccessToken);
    isRefreshing = false;

    return newAccessToken;
  }
};

const fetchWithToken = async (
  endpoint: string,
  requestInit: RequestInit,
  errorMessage: string = "",
) => {
  if (!navigator.onLine) {
    throw new HTTPError(MESSAGES.ERROR.OFFLINE);
  }

  let response = await fetch(`${serverUrl}${endpoint}`, requestInit);
  let text = await response.text();
  let data = text ? JSON.parse(text) : null;

  if (response.status === 401 && data.exceptionType === "TOKEN_EXPIRED") {
    if (isRefreshing) {
      new Promise<string>((resolve, reject) => {
        failedQueue.push({ resolve, reject });
      }).then(async (token) => {
        requestInit.headers = {
          ...requestInit.headers,
          Authorization: `Bearer ${token}`,
        };

        response = await fetch(`${serverUrl}${endpoint}`, requestInit);

        if (!response.ok && response.status !== 401) {
          throw new HTTPError(data.message || MESSAGES.ERROR.POST_REFRESH);
        }
      });
    }

    isRefreshing = true;

    const newAccessToken = await refreshAccessToken();
    requestInit.headers = {
      ...requestInit.headers,
      Authorization: `Bearer ${newAccessToken}`,
    };

    response = await fetch(`${serverUrl}${endpoint}`, requestInit);
    text = await response.text();
    data = text ? JSON.parse(text) : null;

    if (!response.ok && response.status !== 401) {
      throw new HTTPError(data.message || MESSAGES.ERROR.POST_REFRESH);
    }
  }

  if (!response.ok && response.status !== 401) {
    throw new HTTPError(data.message || errorMessage);
  }

  return text ? data : response;
};

const createRequestInit = (
  method: Method,
  headers: Record<string, string>,
  body: object | null,
): RequestInit => {
  const token = localStorage.getItem("accessToken");

  return {
    method,
    headers: {
      ...headers,
      Authorization: token ? `Bearer ${token}` : "",
      "Content-Type": "application/json",
    },
    body: body ? JSON.stringify(body) : null,
    credentials: "include",
  };
};

const apiClient = {
  get: ({ endpoint, headers = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "GET", endpoint, headers, errorMessage }),

  post: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "POST", endpoint, headers, body, errorMessage }),

  put: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "PUT", endpoint, headers, body, errorMessage }),

  patch: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "PATCH", endpoint, headers, body, errorMessage }),

  delete: ({ endpoint, headers = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "DELETE", endpoint, headers, errorMessage }),

  request: async ({
    method,
    endpoint,
    headers = {},
    body = null,
    errorMessage = "",
  }: RequestProps) => {
    const requestInit = createRequestInit(method, headers, body);
    return await fetchWithToken(endpoint, requestInit, errorMessage);
  },
};

export default apiClient;
