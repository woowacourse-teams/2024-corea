// 공통 타입 정의
export type Classification = "ALL" | "FRONTEND" | "BACKEND" | "ANDROID";

export type RoomStatus = "OPEN" | "CLOSE" | "PROGRESS" | "FAIL";
export type RoomStatusCategory = "participated" | "progress" | "opened" | "closed";

export type ParticipationStatus =
  | "NOT_PARTICIPATED"
  | "PARTICIPATED"
  | "MANAGER"
  | "PULL_REQUEST_NOT_SUBMITTED";

export type MemberRole = "BOTH" | "REVIEWER" | "REVIEWEE" | "NONE";

// 통합된 roomInfo
export interface BaseRoomInfo {
  title: string;
  content: string;
  repositoryLink: string;
  thumbnailLink: string;
  matchingSize: number;
  keywords: string[];
  limitedParticipants: number;
  classification: Classification;
  isPublic: boolean;
  recruitmentDeadline: string;
  reviewDeadline: string;
  memberRole: MemberRole;
}

export interface RoomInfo extends BaseRoomInfo {
  roomId: number;
  manager: string;
  reviewerCount: number;
  bothCount: number;
  roomStatus: RoomStatus;
  participationStatus: ParticipationStatus;
  message: string;
}

// 요청(Request) 구조
export interface RoomInfoRequest {
  title: string;
  content: string;
  thumbnailLink: string;
  matchingSize: number;
  keywords: string[];
  limitedParticipants: number;
}

export interface DeadlineRequest {
  recruitmentDeadline: string;
  reviewDeadline: string;
}

export interface RepositoryRequest {
  repositoryLink: string;
  classification: Classification;
  isPublic: boolean;
}

export interface ManagerParticipationRequest {
  memberRole: MemberRole;
  matchingSize: number;
}

export interface RoomCreateRequest {
  roomInfoRequest: RoomInfoRequest;
  deadlineRequest: DeadlineRequest;
  repositoryRequest: RepositoryRequest;
  managerParticipationRequest: ManagerParticipationRequest;
}

// 응답(Response) 구조
export interface RoomInfoResponse extends RoomInfoRequest {
  roomId: number;
  manager: string;
  roomStatus: RoomStatus;
  reviewerCount: number;
  bothCount: number;
  message: string;
}

export interface DeadlineResponse extends DeadlineRequest {}

export interface RepositoryResponse extends RepositoryRequest {}

export interface ParticipationResponse {
  participationStatus: ParticipationStatus;
  memberRole: MemberRole;
  matchingSize: number;
}

export interface RoomDetailResponse {
  roomInfoResponse: RoomInfoResponse;
  deadlineResponse: DeadlineResponse;
  repositoryResponse: RepositoryResponse;
  participationResponse: ParticipationResponse;
}

export interface RoomListInfo {
  rooms: RoomDetailResponse[];
  isLastPage: boolean;
  pageNumber: number;
}
