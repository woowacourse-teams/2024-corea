export type RankingClassification = "re" | "an" | "be" | "fe";

export interface RankingData {
  rank: number;
  nickname: string;
  githubLink: string;
  givenReviewCount: number;
  averageRating: number;
  profileImage: string;
  classification: string;
}

export interface RankingAllData {
  re: RankingData[];
  an: RankingData[];
  be: RankingData[];
  fe: RankingData[];
}
