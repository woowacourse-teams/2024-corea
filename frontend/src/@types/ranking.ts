export interface RankingData {
  rankingNumber: number;
  nickname: string;
  githubLink: string;
  givenReviewCount: number;
  averageRating: number;
  profileImage: string;
  classification: "re" | "an" | "be" | "fe";
}

export interface RankingAllData {
  re: RankingData;
  an: RankingData;
  be: RankingData;
  fe: RankingData;
}
