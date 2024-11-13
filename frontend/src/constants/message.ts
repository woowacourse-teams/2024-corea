const GUIDANCE_MESSAGES = {
  EMPTY_REVIEWER: "아직 리뷰어가 매칭되지 않았습니다!",
  EMPTY_REVIEWEE: "아직 리뷰이가 매칭되지 않았습니다!",
  FAIL_MATCHED: "매칭에 실패한 방입니다.",
  REVIEWEE_FAIL_MATCHED: "리뷰이가 매칭되지 않았습니다.",
  SUB_DESCRIPTION: "조금만 기다려주세요🤗",
  DELETE_ROOM: "정말 방을 삭제하시겠습니까?\n모집 마감 후엔 방을 삭제할 수 없습니다.",
  EXIT_ROOM: "정말 방을 나가시겠습니까?\n모집 마감 전까진 언제든지 다시 참여할 수 있습니다.",
  CREATE_ROOM: "방을 생성합니다.\n모집 마감 전까지 방 정보를 수정할 수 있습니다.",
  EDIT_ROOM: "방 정보를 수정합니다.\n모집 마감 전까지 방 정보를 수정할 수 있습니다.",
  EMPTY_PARTICIPANTS: "참여자 목록은 매칭이 시작된 이후 공개됩니다.",
  ZERO_PARTICIPANTS: "이 방의 참여자가 없습니다.",
  NO_PARTICIPANTS: "매칭 실패로 인해 참여자 목록을 볼 수 없습니다.",
  ONLY_REVIEWER: "리뷰어로만 참여 중입니다!",
  PULL_REQUEST_NOT_SUBMITTED: "PR을 제출하지 않아 매칭되지 않았습니다.",
  NO_ALARM_LIST: "표시할 알림이 없습니다.",
};

const ERROR_MESSAGES = {
  OFFLINE: "오프라인 상태입니다. 네트워크를 확인해주세요.",

  // auth
  POST_LOGIN: "로그인 도중 오류가 발생했습니다. 다시 로그인 해주세요.",
  POST_REFRESH: "토큰이 만료되었습니다. 다시 로그인 해주세요.",
  POST_LOGOUT: "로그아웃 도중 오류가 발생했습니다. 다시 로그아웃 해주세요.",

  // rooms
  GET_PARTICIPATED_ROOM_LIST: "참여하는 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_PROGRESS_ROOM_LIST: "진행 중인 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_OPENED_ROOM_LIST: "모집 중인 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_CLOSED_ROOM_LIST: "모집 완료 방 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_SEARCH_ROOM_LIST: "방 검색 결과를 불러오는 도중 에러가 발생하였습니다.",
  GET_ROOM_DETAIL_INFO: "방 상세정보를 불러오는 도중 에러가 발생하였습니다.",
  POST_CREATE_ROOM: "방 생성하기를 실패했습니다.",
  PUT_EDIT_ROOM: "방 정보 수정하기를 실패했습니다.",
  POST_PARTICIPATE_IN: "방 참여하기를 실패했습니다.",
  DELETE_PARTICIPATE_IN: "방 참여 취소하기를 실패했습니다.",
  DELETE_PARTICIPATED_ROOM: "방 삭제하기를 실패했습니다.",
  GET_PARTICIPANT_LIST: "방 참여자 목록을 불러오는 도중 에러가 발생하였습니다.",

  // reviews
  POST_REVIEW_COMPLETE: "코드리뷰 완료 요청에 실패했습니다.",
  GET_MY_REVIEWERS: "리뷰어 목록을 불러오는 도중 에러가 발생하였습니다.",
  GET_MY_REVIEWEES: "리뷰이 목록을 불러오는 도중 에러가 발생하였습니다.",
  POST_REVIEW_URGE: "코드리뷰 재촉 요청에 실패했습니다.",

  // profile
  GET_PROFILE: "프로필 불러오는 도중 에러가 발생하였습니다.",

  // feedbacks
  // 리뷰어 -> 리뷰이
  GET_REVIEWEE_FEEDBACK: "리뷰이 피드백을 불러오는 도중 에러가 발생하였습니다. ",
  POST_REVIEWEE_FEEDBACK: "리뷰이 피드백 작성에 실패했습니다.",
  PUT_REVIEWEE_FEEDBACK: "리뷰이 피드백 수정에 실패했습니다.",
  // 리뷰이 -> 리뷰어
  GET_REVIEWER_FEEDBACK: "리뷰어 피드백을 불러오는 도중 에러가 발생하였습니다. ",
  POST_REVIEWER_FEEDBACK: "리뷰어 피드백 작성에 실패했습니다.",
  PUT_REVIEWER_FEEDBACK: "리뷰어 피드백 수정에 실패했습니다.",
  //피드백 모아보기
  GET_RECEIVED_FEEDBACK: "받은 피드백 불러오는 도중 에러가 발생하였습니다.",
  GET_DELIVERED_FEEDBACK: "받은 피드백 불러오는 도중 에러가 발생하였습니다.",

  // ranking
  GET_RANKING: "랭킹을 불러오는 도중 에러가 발생하였습니다.",

  // alaram
  GET_ALARM_COUNT: "읽지 않은 알림 개수를 불러오는 도중 에러가 발생하였습니다.",
  GET_ALARM_LIST: "알림 목록을 불러오는 도중 에러가 발생하였습니다.",
  POST_ALARM_CHECKED: "알림을 읽음 상태로 변경하기를 실패했습니다.",

  // errorBoundary
  BOUNDARY_TOTAL: "일시적인 에러가 발생했습니다.",
  BOUNDARY_API: "네트워크 에러가 발생했습니다.",
};

const SUCCESS_MESSAGES = {
  POST_REVIEW_COMPLETE: "정상적으로 코드리뷰를 완료했습니다.",
  POST_REVIEW_FEEDBACK: "정상적으로 피드백을 작성하였습니다.",
  POST_CREATE_ROOM: "정상적으로 방을 생성하였습니다.",
  PUT_EDIT_ROOM: "정상적으로 방 정보가 수정되었습니다.",
  POST_PARTICIPATE_IN: "정상적으로 방에 참여하였습니다.",
  DELETE_PARTICIPATE_IN: "정상적으로 방 참여를 취소하였습니다.",
  DELETE_PARTICIPATED_ROOM: "정상적으로 방을 삭제하였습니다.",
  PUT_REVIEW_FEEDBACK: "정상적으로 피드백을 수정하였습니다.",
  POST_REVIEW_URGE: "정상적으로 코드리뷰 재촉하기를 했습니다.",
};

const MESSAGES = {
  GUIDANCE: GUIDANCE_MESSAGES,
  ERROR: ERROR_MESSAGES,
  SUCCESS: SUCCESS_MESSAGES,
};

export default MESSAGES;
