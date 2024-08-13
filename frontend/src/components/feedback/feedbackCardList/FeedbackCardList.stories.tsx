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
    userFeedback: {
      description: "유저별로 작성한 피드백 정보",
    },
  },
} satisfies Meta<typeof FeedbackCardList>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    userFeedback: {
      민수: [
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "민수",
          feedbackKeywords: ["string"],
          evaluationPoint: 1,
          feedbackText: "string",
        },
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "민수",
          feedbackKeywords: ["string"],
          evaluationPoint: 2,
          feedbackText: "string",
        },
      ],
      복순이: [
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "복순이",
          feedbackKeywords: ["string"],
          evaluationPoint: 1,
          feedbackText: "string",
        },
      ],
      영희: [
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "영희",
          feedbackKeywords: ["string"],
          evaluationPoint: 1,
          feedbackText: "string",
        },
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "영희",
          feedbackKeywords: ["string"],
          evaluationPoint: 2,
          feedbackText: "string",
        },
      ],
      철수: [
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "철수",
          feedbackKeywords: ["string"],
          evaluationPoint: 1,
          feedbackText: "string",
        },
      ],
      순자: [
        {
          feedbackId: 0,
          roomId: 0,
          receiverId: 0,
          profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
          username: "순자",
          feedbackKeywords: ["string"],
          evaluationPoint: 1,
          feedbackText: "string",
        },
      ],
    },
  },
};
