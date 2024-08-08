import { API_ENDPOINTS } from "./endpoints";
import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";

type Method = "GET" | "POST" | "PATCH" | "DELETE";

interface ApiProps {
  endpoint: string;
  headers?: Record<string, string>;
  body?: object | null;
  errorMessage?: string;
}

interface RequestProps extends ApiProps {
  method: Method;
}

interface QueueItem {
  resolve: (value: string | PromiseLike<string>) => void;
  reject: (reason?: Error) => void;
}

const refreshToken = localStorage.getItem("refreshToken");

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

const refreshAccessToken = async (): Promise<string | void> => {
  try {
    const response = await fetch(`${serverUrl}${API_ENDPOINTS.REFRESH}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ refreshToken }),
    });

    if (!response.ok) {
      const error = new Error(MESSAGES.ERROR.POST_REFRESH);
      processQueue(error, null);
      throw error;
    }

    const authHeader = response.headers.get("Authorization");
    const newAccessToken = authHeader?.split(" ")[1];

    if (!newAccessToken) {
      const error = new Error(MESSAGES.ERROR.POST_REFRESH);
      processQueue(error, null);
      throw error;
    }

    localStorage.setItem("accessToken", newAccessToken);
    processQueue(null, newAccessToken);
    isRefreshing = false;

    return newAccessToken;
  } catch (e) {
    isRefreshing = false;
    failedQueue = [];
    localStorage.clear();
  }
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
  };
};

const fetchWithErrorHandling = async (
  endpoint: string,
  requestInit: RequestInit,
  errorMessage: string = "",
) => {
  if (!navigator.onLine) {
    throw new Error(MESSAGES.ERROR.OFFLINE);
  }

  const response = await fetch(`${serverUrl}${endpoint}`, requestInit);

  if (response.status === 401) {
    if (isRefreshing) {
      return new Promise<string>((resolve, reject) => {
        failedQueue.push({ resolve, reject });
      })
        .then((token) => {
          requestInit.headers = {
            ...requestInit.headers,
            Authorization: `Bearer ${token}`,
          };
          return fetch(`${serverUrl}${endpoint}`, requestInit);
        })
        .then((response) => {
          if (!response.ok) {
            throw new Error(errorMessage || `Error: ${response.status} ${response.statusText}`);
          }
          return response.json();
        });
    }

    isRefreshing = true;
    return refreshAccessToken()
      .then((newAccessToken) => {
        if (!newAccessToken) {
          throw new Error(errorMessage || "Failed to refresh token");
        }
        requestInit.headers = {
          ...requestInit.headers,
          Authorization: `Bearer ${newAccessToken}`,
        };
        return fetch(`${serverUrl}${endpoint}`, requestInit);
      })
      .then((response) => {
        if (!response.ok) {
          throw new Error(errorMessage || `Error: ${response.status} ${response.statusText}`);
        }
        return response.json();
      });
  }

  if (!response.ok) {
    throw new Error(errorMessage || `Error: ${response.status} ${response.statusText}`);
  }

  const text = await response.text();
  return text ? JSON.parse(text) : response;
};

const apiClient = {
  get: ({ endpoint, headers = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "GET", endpoint, headers, errorMessage }),

  post: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) =>
    apiClient.request({ method: "POST", endpoint, headers, body, errorMessage }),

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
    return await fetchWithErrorHandling(endpoint, requestInit, errorMessage);
  },
};

export default apiClient;
