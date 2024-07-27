const GUIDANCE_MESSAGES = {
  EMPTY_REVIEWER: "아직 리뷰어가 매칭되지 않았습니다! 조금만 기다려주세요🤗",
  EMPTY_REVIEWEE: "아직 리뷰이가 매칭되지 않았습니다! 조금만 기다려주세요🤗",
};

const ERROR_MESSAGES = {
  OFFLINE: "오프라인 상태입니다. 네트워크를 확인해주세요.",

  // rooms
  GET_PARTICIPATED_ROOM_LIST: "참여하는 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_OPENED_ROOM_LIST: "모집 중인 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_CLOSED_ROOM_LIST: "모집 완료 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_ROOM_DETAIL_INFO: "방 상세정보를 불러오는 도중 에러가 발생하였습니다.",

  // review
  GET_MY_REVIEWERS: "리뷰어 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_MY_REVIEWEES: "리뷰이 목록을 불러오는 도중 에러가 발생하였습니다.",
};

const MESSAGES = {
  GUIDANCE: GUIDANCE_MESSAGES,
  ERROR: ERROR_MESSAGES,
};

export default MESSAGES;
