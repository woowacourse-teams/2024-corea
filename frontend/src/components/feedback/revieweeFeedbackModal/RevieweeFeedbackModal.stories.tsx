import type { Meta, StoryObj } from "@storybook/react";
import RevieweeFeedbackModal from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal";
import { FeedbackModalType } from "@/utils/feedbackUtils";

const meta = {
  title: "feedback/RevieweeFeedbackModal",
  component: RevieweeFeedbackModal,
  parameters: {
    docs: {
      description: {
        component: "리뷰이 피드백 작성을 위한 모달 컴포넌트",
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
      id: 1,
      title: "React 프로젝트 코드 리뷰",
      keywords: ["React", "TypeScript", "Styled-Components"],
      isClosed: false,
    },
    reviewee: {
      userId: 1,
      username: "exampleUser",
      link: "https://github.com/example/project/pull/1",
      isReviewed: false,
      isWrited: false,
    },
    modalType: "create" as FeedbackModalType,
    buttonText: "피드백 작성",
  },
} satisfies Meta<typeof RevieweeFeedbackModal>;

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

export const EditMode: Story = {
  args: {
    modalType: "edit",
    buttonText: "피드백 수정",
  },
};

export const ViewMode: Story = {
  args: {
    modalType: "view",
    buttonText: "피드백 확인",
  },
};
