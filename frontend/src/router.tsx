import PrivateRoute from "./components/routes/PrivateRoute";
import CallbackPage from "./pages/callback/CallbackPage";
import FeedbackPage from "./pages/feedback/FeedbackPage";
import ProfilePage from "./pages/profile/ProfilePage";
// import RankingPage from "./pages/ranking/RankingPage";
import RoomCreatePage from "./pages/roomCreate/RoomCreatePage";
import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import Layout from "@/components/layout/Layout";
import MainPage from "@/pages/main/MainPage";
import RoomDetailPage from "@/pages/roomDetail/RoomDetailPage";
import UserProfile from "@/pages/userProfile/UserProfile";
import { Sentry } from "@/Sentry";

const GuidePage = lazy(() => import("./pages/guide/GuidePage"));

const sentryCreateBrowserRouter = Sentry.wrapCreateBrowserRouter(createBrowserRouter);

const router = sentryCreateBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <MainPage />,
      },
      {
        path: `callback`,
        element: <CallbackPage />,
      },
      {
        path: `guide`,
        element: <GuidePage />,
      },
      {
        path: `profile/:username`,
        element: <UserProfile />,
      },
      // {
      //   path: `ranking`,
      //   element: <RankingPage />,
      // },
      {
        element: <PrivateRoute />,
        children: [
          {
            path: `rooms/:id`,
            element: <RoomDetailPage />,
          },
          {
            path: `rooms/create`,
            element: <RoomCreatePage />,
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
    ],
  },
]);

export default router;
