export interface RankingData {
  nickname: string;
  githubLink: string;
  givenReviewCount: number;
  averageRating: number;
  classification: string;
}

export interface RankingAllData {
  re: RankingData;
  an: RankingData;
  be: RankingData;
  fe: RankingData;
}
