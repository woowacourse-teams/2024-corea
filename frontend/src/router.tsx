import PrivateRoute from "./components/routes/PrivateRoute";
import CallbackPage from "./pages/callback/CallbackPage";
import FeedbackPage from "./pages/feedback/FeedbackPage";
import RevieweeFeedbackPage from "./pages/feedbackForm/RevieweeFeedbackPage";
import ReviewerFeedbackPage from "./pages/feedbackForm/ReviewerFeedbackPage";
import ProfilePage from "./pages/profile/ProfilePage";
import RoomCreatePage from "./pages/roomForm/RoomCreatePage";
// import RankingPage from "./pages/ranking/RankingPage";
import RoomEditPage from "./pages/roomForm/RoomEditPage";
import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import Layout from "@/components/layout/Layout";
import NotFoundRoute from "@/components/routes/NotFoundRoute";
import IntroPage from "@/pages/intro/IntroPage";
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
      { path: `intro`, element: <IntroPage /> },
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
            path: `rooms/edit/:roomId`,
            element: <RoomEditPage />,
          },
          {
            path: `feedback`,
            element: <FeedbackPage />,
          },
          {
            path: `feedback/reviewer/:roomId`,
            element: <ReviewerFeedbackPage />,
          },
          {
            path: `feedback/reviewee/:roomId`,
            element: <RevieweeFeedbackPage />,
          },
          {
            path: `profile`,
            element: <ProfilePage />,
          },
        ],
      },
      {
        path: "*",
        element: <NotFoundRoute />,
      },
    ],
  },
]);

export default router;
