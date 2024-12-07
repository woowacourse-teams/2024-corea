import { CreateRoomInfo, RoomCreateRequest, RoomDetailResponse, RoomInfo } from "@/@types/roomInfo";

// RoomDetailResponse를 RoomInfo로 변환
export const mapRoomDetailResponseToRoomInfo = (response: RoomDetailResponse): RoomInfo => ({
  id: response.roomInfoResponse.roomId,
  title: response.roomInfoResponse.title,
  content: response.roomInfoResponse.content,
  repositoryLink: response.repositoryResponse.repositoryLink,
  thumbnailLink: response.roomInfoResponse.thumbnailLink,
  matchingSize: response.roomInfoResponse.matchingSize,
  keywords: response.roomInfoResponse.keywords,
  limitedParticipants: response.roomInfoResponse.limitedParticipants,
  classification: response.repositoryResponse.classification,
  isPublic: response.repositoryResponse.isPublic,
  recruitmentDeadline: response.deadlineResponse.recruitmentDeadline,
  reviewDeadline: response.deadlineResponse.reviewDeadline,
  manager: response.roomInfoResponse.manager,
  reviewerCount: response.roomInfoResponse.reviewerCount,
  bothCount: response.roomInfoResponse.bothCount,
  roomStatus: response.roomInfoResponse.roomStatus,
  participationStatus: response.participationResponse.participationStatus,
  memberRole: response.participationResponse.memberRole,
  message: response.roomInfoResponse.message,
});

// RoomInfo를 RoomCreateRequest로 변환
export const mapRoomInfoToRoomCreateRequest = (info: CreateRoomInfo): RoomCreateRequest => ({
  roomInfoRequest: {
    title: info.title,
    content: info.content,
    thumbnailLink: info.thumbnailLink,
    matchingSize: info.matchingSize,
    keywords: info.keywords,
    limitedParticipants: info.limitedParticipants,
  },
  deadlineRequest: {
    recruitmentDeadline: info.recruitmentDeadline,
    reviewDeadline: info.reviewDeadline,
  },
  repositoryRequest: {
    repositoryLink: info.repositoryLink,
    classification: info.classification,
    isPublic: info.isPublic,
  },
  managerParticipationRequest: {
    memberRole: info.managerMemberRole,
    matchingSize: info.managerMatchingSize,
  },
});
