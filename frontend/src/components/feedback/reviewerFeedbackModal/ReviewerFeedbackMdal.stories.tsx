import type { Meta, StoryObj } from "@storybook/react";
import ReviewerFeedbackModal from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal";

const meta = {
  title: "feedback/ReviewerFeedbackModal",
  component: ReviewerFeedbackModal,
  parameters: {
    docs: {
      description: {
        component: "리뷰어 피드백 작성을 위한 모달 컴포넌트",
      },
    },
    viewport: {
      defaultViewport: "responsive",
    },
  },
  args: {
    isOpen: true,
    onClose: () => {},
    roomInfo: {
      title: "React 프로젝트 코드 리뷰",
      keywords: ["React", "TypeScript", "Styled-Components"],
    },
    buttonType: "create",
  },
} satisfies Meta<typeof ReviewerFeedbackModal>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const SmallViewport: Story = {
  parameters: {
    viewport: {
      defaultViewport: "mobile1",
    },
  },
};

export const MediumViewport: Story = {
  parameters: {
    viewport: {
      defaultViewport: "tablet",
    },
  },
};

export const LargeViewport: Story = {
  parameters: {
    viewport: {
      defaultViewport: "desktop",
    },
  },
};
