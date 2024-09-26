export type Classification = "ALL" | "FRONTEND" | "BACKEND" | "ANDROID";

export type RoomStatus = "OPEN" | "CLOSE" | "PROGRESS";

export type ParticipationStatus = "NOT_PARTICIPATED" | "PARTICIPATED" | "MANAGER";

interface BaseRoomInfo {
  title: string;
  content: string;
  repositoryLink: string;
  thumbnailLink: string;
  matchingSize: number;
  keywords: string[];
  limitedParticipants: number;
  recruitmentDeadline: string;
  reviewDeadline: string;
}
export interface CreateRoomInfo extends BaseRoomInfo {
  classification: Classification | "";
}

export interface RoomInfo extends BaseRoomInfo {
  id: number;
  manager: string;
  currentParticipants: number;
  roomStatus: RoomStatus;
  participationStatus: ParticipationStatus;
}

export interface RoomListInfo {
  rooms: RoomInfo[];
  isLastPage: boolean;
  pageNumber: number;
}
