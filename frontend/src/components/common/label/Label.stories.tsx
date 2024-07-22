import Label from "./Label";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "common/Label",
  component: Label,
  parameters: {
    docs: {
      description: {
        component: "라벨 컴포넌트",
      },
    },
  },
  argTypes: {
    type: {
      description: "라벨 타입",
      control: { type: "select" },
      options: ["keyword", "open", "close"],
    },
    text: {
      description: "라벨 텍스트 (키워드 타입에만 적용)",
      control: { type: "text" },
    },
  },
} satisfies Meta<typeof Label>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Keyword: Story = {
  args: {
    type: "keyword",
    text: "FRONTEND",
  },
};

export const Open: Story = {
  args: {
    type: "open",
  },
};

export const Close: Story = {
  args: {
    type: "close",
  },
};
