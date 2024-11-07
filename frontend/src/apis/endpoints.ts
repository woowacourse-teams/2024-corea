export const API_ENDPOINTS = {
  // auth
  LOGIN: "/login",
  REFRESH: "/refresh",
  LOGOUT: "/logout",

  // rooms
  PARTICIPATED_ROOMS: "/rooms/participated",
  PROGRESS_ROOMS: "/rooms/progress",
  OPENED_ROOMS: "/rooms/opened",
  CLOSED_ROOMS: "/rooms/closed",
  SEARCH_ROOMS: "/rooms/search",
  PARTICIPATE_IN: (roomId: number) => `/participate/${roomId}`,
  ROOMS: "/rooms",
  PARTICIPANT_LIST: (roomId: number) => `/rooms/${roomId}/participants`,

  // reviews
  REVIEWERS: (roomId: number) => `/rooms/${roomId}/reviewers`,
  REVIEWEES: (roomId: number) => `/rooms/${roomId}/reviewees`,
  REVIEW_COMPLETE: "/review/complete",

  // profile
  PROFILE: "/user/profile",
  USER_PROFILE: (userName: string) => `/user/profile/by?username=${userName}`,

  // feedbacks
  // 리뷰어->리뷰이
  REVIEWEE_FEEDBACK: (roomId: number) => `/rooms/${roomId}/develop/feedbacks`,
  PUT_REVIEWEE_FEEDBACK: (roomId: number, feedbackId: number) =>
    `/rooms/${roomId}/develop/feedbacks/${feedbackId}`,
  // 리뷰이->리뷰어
  REVIEWER_FEEDBACK: (roomId: number) => `/rooms/${roomId}/social/feedbacks`,
  PUT_REVIEWER_FEEDBACK: (roomId: number, feedbackId: number) =>
    `/rooms/${roomId}/social/feedbacks/${feedbackId}`,
  RECEIVED_FEEDBACK: "/user/feedbacks/received",
  DELIVERED_FEEDBACK: "/user/feedbacks/delivered",

  // ranking
  RANKING: "/ranks/board",
};
