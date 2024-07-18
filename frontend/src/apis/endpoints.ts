export const API_ENDPOINTS = {
  PARTICIPATED_ROOMS: "/rooms/participated",
  OPENED_ROOMS: "/rooms/opened",
  CLOSED_ROOMS: "/rooms/closed",
  ROOMS: "/rooms",
  MY: "/my",
  REVIEW_COMPLETE: "/review/complete",
  REVIEWERS(roomId: number) {
    return `/rooms/${roomId}/reviewers`;
  },
  REVIEWEES(roomId: number) {
    return `/rooms/${roomId}/reviewees`;
  },
};
