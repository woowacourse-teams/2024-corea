export interface ParticipantInfo {
  githubId: string;
  username: string;
  prLink: string;
  thumbnailLink: string;
}

export interface ParticipantListInfo {
  participants: ParticipantInfo[];
  size: number;
}
