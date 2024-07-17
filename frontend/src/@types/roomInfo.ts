export interface RoomInfo {
  id: number;
  title: string;
  content: string;
  matchingSize: number;
  repositoryLink: string;
  thumbnailLink: string;
  keywords: string[];
  currentParticipantSize: number;
  maximumParticipantSize: number;
  manager: string;
  recruitmentDeadline: string;
  reviewDeadline: string;
  isParticipated: boolean;
  isClosed: boolean;
}
