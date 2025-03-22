import NotFoundPage from "./NotFoundPage";
import { createBrowserRouter } from "react-router-dom";
import Layout from "@/components/layout/Layout";
import CallbackPage from "@/pages/callback/CallbackPage";
import IntroPage from "@/pages/intro/IntroPage";
import MainPage from "@/pages/main/MainPage";
import UserProfilePage from "@/pages/userProfile/UserProfile";
import { Sentry } from "@/Sentry";
import PrivateRoute from "@/router/PrivateRoute";
import * as Lazy from "@/router/lazy";

const sentryCreateBrowserRouter = Sentry.wrapCreateBrowserRouter(createBrowserRouter);

const router = sentryCreateBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <NotFoundPage />,
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
        element: <Lazy.GuidePage />,
      },
      {
        path: `profile/:username`,
        element: <UserProfilePage />,
      },
      // {
      //   path: `ranking`,
      //   element: <RankingPage />,
      // },
      {
        element: <PrivateRoute />,
        children: [
          {
            path: `alarm`,
            element: <Lazy.AlarmPage />,
          },
          {
            path: `rooms/:id`,
            element: <Lazy.RoomDetailPage />,
          },
          {
            path: `rooms/create`,
            element: <Lazy.RoomCreatePage />,
          },
          {
            path: `rooms/edit/:roomId`,
            element: <Lazy.RoomEditPage />,
          },
          {
            path: `feedback`,
            element: <Lazy.FeedbackPage />,
          },
          {
            path: `/rooms/:roomId/feedback/reviewer`,
            element: <Lazy.ReviewerFeedbackPage />,
          },
          {
            path: `/rooms/:roomId/feedback/reviewee`,
            element: <Lazy.RevieweeFeedbackPage />,
          },
          {
            path: `profile`,
            element: <Lazy.ProfilePage />,
          },
        ],
      },
    ],
  },
]);

export default router;
