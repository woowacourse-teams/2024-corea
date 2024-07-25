import axios from "axios";
import { serverUrl } from "@/config/serverUrl";

const apiClient = axios.create({
  baseURL: serverUrl,
});

// api 요청하기 전 수행
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("user");

  if (token) {
    config.headers["Authorization"] = token;
  }

  return config;
});

export default apiClient;
