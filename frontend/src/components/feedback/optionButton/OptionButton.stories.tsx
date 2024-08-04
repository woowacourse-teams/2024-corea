import type { Meta, StoryObj } from "@storybook/react";
import React from "react";
import OptionButton from "@/components/feedback/optionButton/OptionButton";

const meta = {
  title: "feedback/OptionButton",
  component: OptionButton,
  parameters: {
    docs: {
      description: {
        component: "피드백 관련 여러 옵션을 선택할 수 있는 버튼 컴포넌트",
      },
    },
  },
} satisfies Meta<typeof OptionButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  render: () => <OptionButton />,
};
