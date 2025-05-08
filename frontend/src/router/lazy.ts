import { lazy } from "react";

export const GuidePage = lazy(
  () => import(/* webpackChunkName: "GuidePage" */ "@/pages/guide/GuidePage"),
);

export const AlarmPage = lazy(
  () => import(/* webpackChunkName: "AlarmPage" */ "@/pages/alarm/AlarmPage"),
);

export const ProfilePage = lazy(
  () => import(/* webpackChunkName: "ProfilePage" */ "@/pages/profile/ProfilePage"),
);

export const RoomDetailPage = lazy(
  () => import(/* webpackChunkName: "RoomDetailPage" */ "@/pages/roomDetail/RoomDetailPage"),
);

export const RoomCreatePage = lazy(
  () => import(/* webpackChunkName: "RoomCreatePage" */ "@/pages/roomForm/RoomCreatePage"),
);

export const RoomEditPage = lazy(
  () => import(/* webpackChunkName: "RoomEditPage" */ "@/pages/roomForm/RoomEditPage"),
);

export const FeedbackPage = lazy(
  () => import(/* webpackChunkName: "FeedbackPage" */ "@/pages/feedback/FeedbackPage"),
);

export const ReviewerFeedbackPage = lazy(
  () =>
    import(
      /* webpackChunkName: "ReviewerFeedbackPage" */ "@/pages/feedbackForm/ReviewerFeedbackPage"
    ),
);

export const RevieweeFeedbackPage = lazy(
  () =>
    import(
      /* webpackChunkName: "RevieweeFeedbackPage" */ "@/pages/feedbackForm/RevieweeFeedbackPage"
    ),
);
