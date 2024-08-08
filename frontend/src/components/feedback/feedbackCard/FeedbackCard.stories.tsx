import FeedbackCard from "./FeedbackCard";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "feedback/FeedbackCard",
  component: FeedbackCard,
  parameters: {
    docs: {
      description: {
        component: "피드백 카드 컴포넌트",
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
    username: {
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
    username: "chlwlstlf",
    roomId: 0,
    receiverId: 1,
    feedbackKeywords: ["작업 속도", "PR 본문 메시지", "코드 관심사 분리"],
    feedbackText: `작업 속도가 정말 빠르면서 평소 책을 많이 읽어서 그런지 PR 본문 메시지가 정말 알이 꽉꽉 찼어요.
    
    그리고 코드 관심사 분리라는 단어를 알고 있어서 대화하기 편했어요 ㅎㅎ.
`,
    evaluationPoint: 1,
  },
};
