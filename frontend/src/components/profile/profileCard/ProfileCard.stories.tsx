import type { Meta, StoryObj } from "@storybook/react";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";

const meta = {
  title: "profile/ProfileCard",
  component: ProfileCard,
  parameters: {
    docs: {
      description: {
        component: "마이페이지 프로필 카드 컴포넌트",
      },
    },
  },
  argTypes: {
    profileImage: { description: "프로필 이미지 주소 링크" },
    nickname: { description: "닉네임" },
    receivedReviewCount: { description: "받은 리뷰 갯수" },
    givenReviewCount: { description: "해준 리뷰 갯수" },
    feedbackCount: { description: "받은 피드백 갯수" },
    averageRating: { description: "평균 별점" },
    feedbackKeywords: { description: "피드백 상위 키워드 3개" },
    attitudeScore: { description: "매너 온도 점수", control: { type: "range", min: 0, max: 100 } },
  },
} satisfies Meta<typeof ProfileCard>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    profileImage: "https://avatars.githubusercontent.com/u/63334368?v=4",
    nickname: "chlwlstlf",
    receivedReviewCount: 4,
    givenReviewCount: 9092,
    feedbackCount: 11,
    averageRating: 4.9,
    feedbackKeywords: [
      "잘하고 맛있어요.",
      "오므라이스 김치볶음밥.",
      "친절하고 꼼꼼하게 시술해줘요.",
    ],
    attitudeScore: 98,
  },
};
