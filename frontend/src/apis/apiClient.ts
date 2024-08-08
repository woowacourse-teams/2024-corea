import { serverUrl } from "@/config/serverUrl";
import MESSAGES from "@/constants/message";

type Method = "GET" | "POST" | "PUT" | "PATCH" | "DELETE";

interface ApiProps {
  endpoint: string;
  headers?: Record<string, string>;
  body?: object | null;
  errorMessage?: string;
}

interface RequestProps extends ApiProps {
  method: Method;
}

const createRequestInit = (
  method: Method,
  headers: Record<string, string>,
  body: object | null,
): RequestInit => {
  const token = localStorage.getItem("user");

  return {
    method,
    headers: {
      ...headers,
      Authorization: token || "",
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
  // 오프라인 확인
  if (!navigator.onLine) {
    throw new Error(MESSAGES.ERROR.OFFLINE);
  }

  const response = await fetch(`${serverUrl}${endpoint}`, requestInit);

  if (!response.ok) {
    throw new Error(errorMessage || `Error: ${response.status} ${response.statusText}`);
  }

  const text = await response.text();

  return text ? JSON.parse(text) : response;
};

const apiClient = {
  get: ({ endpoint, headers = {}, errorMessage = "" }: ApiProps) => {
    return apiClient.request({ method: "GET", endpoint, headers, errorMessage });
  },
  post: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) => {
    return apiClient.request({ method: "POST", endpoint, headers, body, errorMessage });
  },
  put: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) => {
    return apiClient.request({ method: "PUT", endpoint, headers, body, errorMessage });
  },
  patch: ({ endpoint, headers = {}, body = {}, errorMessage = "" }: ApiProps) => {
    return apiClient.request({ method: "PATCH", endpoint, headers, body, errorMessage });
  },
  delete: ({ endpoint, headers = {}, errorMessage = "" }: ApiProps) => {
    return apiClient.request({ method: "DELETE", endpoint, headers, errorMessage });
  },
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
