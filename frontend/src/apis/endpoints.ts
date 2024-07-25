export const API_ENDPOINTS = {
  // 메인 페이지
  PARTICIPATED_ROOMS: "/rooms/participated",
  OPENED_ROOMS: "/rooms/opened",
  CLOSED_ROOMS: "/rooms/closed",
  PARTICIPATE_IN: (roomId: number) => `/participate/${roomId}`,

  // 방 상세 페이지
  ROOMS: `/rooms`,
  REVIEWERS: (roomId: number) => `/rooms/${roomId}/reviewers`,
  REVIEWEES: (roomId: number) => `/rooms/${roomId}/reviewees`,
  REVIEW_COMPLETE: "/review/complete",
};
