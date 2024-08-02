import type { Meta, StoryObj } from "@storybook/react";
import React from "react";
import FeedbackButton from "@/components/feedback/feedbackButton/FeedbackButton";

const meta = {
  title: "feedback/FeedbackButton",
  component: FeedbackButton,
  parameters: {
    docs: {
      description: {
        component: "피드백 버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    type: {
      description: "피드백 버튼 타입",
      control: { type: "select" },
      options: ["create", "edit", "view"],
    },
  },
} satisfies Meta<typeof FeedbackButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    type: "create",
  },
};

export const 피드백_작성_버튼: Story = {
  args: {
    type: "create",
  },
};

export const 피드백_수정_버튼: Story = {
  args: {
    type: "edit",
  },
};

export const 피드백_확인_버튼: Story = {
  args: {
    type: "view",
  },
};
