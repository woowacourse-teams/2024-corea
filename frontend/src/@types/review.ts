export interface ReviewInfo {
  userId: number;
  username: string;
  link: string; // PR 링크, Comment 링크
  isReviewed?: boolean;
  isWrited: boolean; // 피드백 작성 여부
}
