export const API_ENDPOINTS = {
  // rooms
  PARTICIPATED_ROOMS: "/rooms/participated",
  OPENED_ROOMS: "/rooms/opened",
  CLOSED_ROOMS: "/rooms/closed",
  PARTICIPATE_IN: (roomId: number) => `/participate/${roomId}`,
  ROOMS: `/rooms`,

  // reviews
  REVIEWERS: (roomId: number) => `/rooms/${roomId}/reviewers`,
  REVIEWEES: (roomId: number) => `/rooms/${roomId}/reviewees`,
  REVIEW_COMPLETE: "/review/complete",

  // profile
  PROFILE: "/user/profile",

  // feedbacks
  REVIEWEE_FEEDBACK: (roomId: number) => `/rooms/${roomId}/reviewee/feedbacks`,
  PUT_REVIEWEE_FEEDBACK: (roomId: number, feedbackId: number) =>
    `/rooms/${roomId}/reviewee/feedbacks/${feedbackId}`,
};
