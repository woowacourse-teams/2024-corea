import FeedbackCardList from "./FeedbackCardList";
import type { Meta, StoryObj } from "@storybook/react";

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

export const Default: Story = {
  args: {
    feedbackData: [
      {
        roomId: 0,
        title: "숫자 골프",
        roomKeywords: ["string", "클린코드", "클린 아키텍쳐"],
        isClosed: true,
        developFeedback: [
          {
            feedbackId: 0,
            roomId: 0,
            receiverId: 0,
            profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
            username: "a",
            feedbackKeywords: ["string"],
            evaluationPoint: 3,
            feedbackText: "string",
          },
          {
            feedbackId: 0,
            roomId: 0,
            receiverId: 0,
            profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
            username: "q",
            feedbackKeywords: ["string"],
            evaluationPoint: 3,
            feedbackText: "string",
          },
        ],
        socialFeedback: [
          {
            feedbackId: 0,
            roomId: 0,
            receiverId: 0,
            profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
            username: "b",
            feedbackKeywords: ["string"],
            evaluationPoint: 3,
            feedbackText: "string",
          },
          {
            feedbackId: 0,
            roomId: 0,
            receiverId: 0,
            profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
            username: "q",
            feedbackKeywords: ["string"],
            evaluationPoint: 3,
            feedbackText: "string",
          },
        ],
      },
      {
        roomId: 1,
        title: "복권 당첨",
        roomKeywords: ["티디딕", "연금 복권"],
        isClosed: true,
        developFeedback: [
          {
            feedbackId: 3,
            roomId: 4,
            receiverId: 0,
            profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
            username: "C",
            feedbackKeywords: ["string"],
            evaluationPoint: 3,
            feedbackText: "string",
          },
        ],
        socialFeedback: [
          {
            feedbackId: 0,
            roomId: 0,
            receiverId: 0,
            profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
            username: "D",
            feedbackKeywords: ["string"],
            evaluationPoint: 3,
            feedbackText: "string",
          },
        ],
      },
    ],
  },
};
