export const API_ENDPOINTS = {
  // auth
  LOGIN: "/login",
  REFRESH: "/refresh",

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
};
