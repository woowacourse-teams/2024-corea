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
  isClosed: boolean;
}

export interface RoomListInfo {
  rooms: RoomInfo[];
  isLastPage: boolean;
}
