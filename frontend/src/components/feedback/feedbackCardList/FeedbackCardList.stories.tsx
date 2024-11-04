import FeedbackCardList from "./FeedbackCardList";
import type { Meta, StoryObj } from "@storybook/react";
import { NonEmptyArray } from "@/@types/NonEmptyArray";
import { FeedbackCardData } from "@/@types/feedback";
import deliveredFeedbackInfo from "@/mocks/mockResponse/deliveredFeedbackInfo.json";

const meta = {
  title: "feedback/FeedbackCardList",
  component: FeedbackCardList,
  parameters: {
    docs: {
      description: {
        component: "피드백 카드 리스트 슬라이드 컴포넌트",
      },
    },
  },
  argTypes: {
    feedbackData: {
      description: "피드백 정보",
    },
  },
} satisfies Meta<typeof FeedbackCardList>;

export default meta;

type Story = StoryObj<typeof meta>;

const developFeedbackData = deliveredFeedbackInfo.feedbacks[0].developFeedback.map((feedback) => ({
  ...feedback,
  feedbackKeywords: feedback.feedbackKeywords as unknown as NonEmptyArray<string>,
  evaluationPoint: feedback.evaluationPoint as 1 | 2 | 3 | 4 | 5,
  isWrited: feedback.isWrited as boolean,
})) satisfies FeedbackCardData[];

const socialFeedbackData = deliveredFeedbackInfo.feedbacks[0].socialFeedback.map((feedback) => ({
  ...feedback,
  feedbackKeywords: feedback.feedbackKeywords as unknown as NonEmptyArray<string>,
  evaluationPoint: feedback.evaluationPoint as 1 | 2 | 3 | 4 | 5,
  isWrited: feedback.isWrited as boolean,
})) satisfies FeedbackCardData[];

export const Default: Story = {
  args: {
    selectedFeedbackType: "받은 피드백",
    feedbackData: [
      {
        roomId: 0,
        title: "숫자 골프",
        roomKeywords: ["string", "클린코드", "클린 아키텍쳐"],
        developFeedback: developFeedbackData,
        socialFeedback: socialFeedbackData,
      },
    ],
  },
};
