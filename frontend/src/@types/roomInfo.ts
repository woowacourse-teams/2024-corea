export interface RoomInfo {
  id: number;
  title: string;
  content: string;
  manager: string;
  repositoryLink: string;
  thumbnailLink: string;
  matchingSize: number;
  keywords: string[];
  currentParticipants: number;
  limitedParticipants: number;
  recruitmentDeadline: string;
  reviewDeadline: string;
  isParticipated: boolean;
  roomStatus: "OPEN" | "CLOSE" | "PROGRESS";
}

export interface RoomListInfo {
  rooms: RoomInfo[];
  isLastPage: boolean;
  pageNumber: number;
}
