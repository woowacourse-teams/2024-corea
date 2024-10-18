export type Classification = "ALL" | "FRONTEND" | "BACKEND" | "ANDROID";

export type RoomStatus = "OPEN" | "CLOSE" | "PROGRESS" | "FAIL";

export type ParticipationStatus =
  | "NOT_PARTICIPATED"
  | "PARTICIPATED"
  | "MANAGER"
  | "PULL_REQUEST_NOT_SUBMITTED";

export type Role = "BOTH" | "REVIEWER" | "REVIEWEE" | "NONE";

interface BaseRoomInfo {
  title: string;
  content: string;
  repositoryLink: string;
  thumbnailLink: string;
  matchingSize: number;
  keywords: string[];
  limitedParticipants: number;
  classification: Classification;
}
export interface CreateRoomInfo extends BaseRoomInfo {
  recruitmentDeadline: Date;
  reviewDeadline: Date;
}

export interface SubmitRoomInfo extends BaseRoomInfo {
  roomId?: number;
  recruitmentDeadline: string;
  reviewDeadline: string;
}

export interface RoomInfo extends BaseRoomInfo {
  id: number;
  manager: string;
  currentParticipants: number;
  roomStatus: RoomStatus;
  participationStatus: ParticipationStatus;
  memberRole: Role;
  recruitmentDeadline: string;
  reviewDeadline: string;
  message: string;
}

export interface RoomListInfo {
  rooms: RoomInfo[];
  isLastPage: boolean;
  pageNumber: number;
}
