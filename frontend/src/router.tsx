import FeedbackPage from "./pages/feedback/FeedbackPage";
import GuidePage from "./pages/guide/GuidePage";
import ProfilePage from "./pages/profile/ProfilePage";
import RankingPage from "./pages/ranking/RankingPage";
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
        path: `rooms/:id`,
        element: <RoomDetailPage />,
      },
      {
        path: `guide`,
        element: <GuidePage />,
      },
      {
        path: `ranking`,
        element: <RankingPage />,
      },
      {
        path: `feedback`,
        element: <FeedbackPage />,
      },
      {
        path: `profile`,
        element: <ProfilePage />,
      },
    ],
  },
]);

export default router;
