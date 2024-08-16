import FeedbackCard from "./FeedbackCard";
import type { Meta, StoryObj } from "@storybook/react";

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
    feedbackCardData: {
      description: "피드백 정보",
    },
    feedbackType: {
      description: "피드백 타입",
    },
  },
} satisfies Meta<typeof FeedbackCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const 기술적_피드백: Story = {
  args: {
    feedbackCardData: {
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
    feedbackType: "develop",
  },
};

export const 소프트스킬_피드백: Story = {
  args: {
    feedbackCardData: 기술적_피드백.args.feedbackCardData,
    feedbackType: "social",
  },
};
