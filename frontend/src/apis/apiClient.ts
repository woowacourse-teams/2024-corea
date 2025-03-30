import { API_ENDPOINTS } from "./endpoints";
import { Method, QueueItem } from "@/@types/apiClient";
import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";
import { ApiError, AuthorizationError, NetworkError } from "@/utils/Errors";

interface ApiProps {
  endpoint: string;
  headers?: Record<string, string>;
  body?: object | null;
  errorMessage?: string;
}

interface RequestProps extends ApiProps {
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

const parseResponse = async (response: Response) => {
  const text = await response.text();
  return text ? JSON.parse(text) : null;
};

const refreshAccessToken = async (): Promise<string | undefined> => {
  const response = await fetch(`${serverUrl}${API_ENDPOINTS.REFRESH}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
  });

  const data = await parseResponse(response);
  const newAccessToken = response.headers.get("Authorization");

  if (!response.ok) {
    isRefreshing = false;
    if (response.status === 401 && data?.exceptionType === "TOKEN_EXPIRED") {
      throw new AuthorizationError(data?.message || MESSAGES.ERROR.POST_REFRESH);
    }
    throw new ApiError(data?.message || MESSAGES.ERROR.POST_REFRESH);
  }

  if (newAccessToken) {
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
    throw new NetworkError(MESSAGES.ERROR.OFFLINE_ACTION);
  }

  let response = await fetch(`${serverUrl}${endpoint}`, requestInit);
  let data = await parseResponse(response);

  if (!response.ok) {
    if (response.status === 401 && data?.exceptionType === "TOKEN_EXPIRED") {
      if (isRefreshing) {
        return new Promise<string>((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        }).then(async (token) => {
          requestInit.headers = {
            ...requestInit.headers,
            Authorization: `Bearer ${token}`,
          };

          const retryResponse = await fetch(`${serverUrl}${endpoint}`, requestInit);
          const retryData = await parseResponse(retryResponse);

          if (!retryResponse.ok) {
            throw new ApiError(retryData?.message || errorMessage);
          }

          return retryData;
        });
      }

      isRefreshing = true;
      const newAccessToken = await refreshAccessToken();

      requestInit.headers = {
        ...requestInit.headers,
        Authorization: `Bearer ${newAccessToken}`,
      };

      response = await fetch(`${serverUrl}${endpoint}`, requestInit);
      data = await parseResponse(response);

      if (!response.ok) {
        throw new ApiError(data?.message || errorMessage);
      }
    } else {
      throw new ApiError(data?.message || errorMessage);
    }
  }

  return { data: data ?? response, headers: response.headers };
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
