import PlusButton from "./PlusButton";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "common/PlusButton",
  component: PlusButton,
  parameters: {
    docs: {
      description: {
        component: "더보기 버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    onClick: { action: "clicked" },
  },
} satisfies Meta<typeof PlusButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    onClick: () => {},
  },
};
