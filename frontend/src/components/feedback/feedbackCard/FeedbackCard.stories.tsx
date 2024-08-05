import FeedbackCard from "./FeedbackCard";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "feedback/FeedbackCard",
  component: FeedbackCard,
  parameters: {
    docs: {
      description: {
        score: "매너 온도 점수",
      },
    },
  },
  argTypes: {
    feedbackId: {
      description: "피드백 카드 id",
    },
    profile: {
      description: "깃허브 프로필 이미지",
    },
    nickname: {
      description: "닉네임",
    },
    feedbackKeywords: {
      description: "피드백 키워드",
    },
    feedbackText: {
      description: "피드백 세부 내용",
    },
    evaluationPoint: {
      description: "피드백 점수",
    },
  },
} satisfies Meta<typeof FeedbackCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    feedbackId: 1,
    profile: "https://avatars.githubusercontent.com/u/63334368?v=4",
    nickname: "chlwlstlf",
    feedbackKeywords: [
      "잘하고 맛있어요.",
      "오므라이스 김치볶음밥.",
      "친절하고 꼼꼼하게 시술해줘요.",
    ],
    feedbackText: `나름 맛있게 해줘서 뿌듯했어요. 
다음에도 좋은 시술 부탁드립니다. 근데 이집 햄버거가 맛있어요. 

트리플베이컨더블빅치즈스페셜자이언트버거 먹으세요`,
    evaluationPoint: 1,
  },
};
