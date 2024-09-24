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
  classification: "AN" | "FE" | "BE";
}

export interface RoomInfo extends BaseRoomInfo {
  id: number;
  manager: string;
  currentParticipants: number;
  isParticipated: boolean;
  roomStatus: "OPEN" | "CLOSE" | "PROGRESS";
}

export interface RoomListInfo {
  rooms: RoomInfo[];
  isLastPage: boolean;
  pageNumber: number;
}
