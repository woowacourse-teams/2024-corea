import { API_ENDPOINTS } from "./apis/endpoints";
import { createBrowserRouter } from "react-router-dom";
import Layout from "@/components/layout/Layout";
import MainPage from "@/pages/main/MainPage";
import RoomDetailPage from "@/pages/roomDetail/RoomDetailPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <MainPage />,
      },
      {
        path: `${API_ENDPOINTS.ROOMS}/:id`,
        element: <RoomDetailPage />,
      },
    ],
  },
]);

export default router;
