import { NonEmptyArray } from "@/@types/NonEmptyArray";

export interface FeedbackCardList {
  readonly roomId: number;
  readonly title: string;
  readonly roomKeywords: NonEmptyArray<string>;
  readonly isClosed: boolean;
  readonly developFeedback: FeedbackCardData[];
  readonly socialFeedback: FeedbackCardData[];
}

export interface FeedbackCardData {
  readonly feedbackId: number;
  readonly profile: string;
  readonly roomId: number;
  readonly receiverId: number;
  readonly username: string;
  readonly feedbackKeywords: NonEmptyArray<string>;
  readonly feedbackText: string;
  readonly evaluationPoint: 1 | 2 | 3 | 4 | 5;
}
