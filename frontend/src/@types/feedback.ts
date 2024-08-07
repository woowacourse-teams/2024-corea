import { NonEmptyArray } from "@/@types/NonEmptyArray";

export interface FeedbackCardList {
  readonly roomId: number;
  readonly title: string;
  readonly roomKeywords: NonEmptyArray<string>;
  readonly isClosed: boolean;
  readonly feedbackData: FeedbackCardData[];
}

export interface FeedbackCardData {
  readonly feedbackId: number;
  readonly profile: string;
  readonly nickname: string;
  readonly feedbackKeywords: NonEmptyArray<string>;
  readonly feedbackText: string;
  readonly evaluationPoint: 1 | 2 | 3 | 4 | 5;
}
