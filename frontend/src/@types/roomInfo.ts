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
  participationStatus: "NOT_PARTICIPATED" | "PARTICIPATED" | "MANAGER"; // 참여X, 참여자, 방장
}

export interface RoomListInfo {
  rooms: RoomInfo[];
  isLastPage: boolean;
  pageNumber: number;
}
