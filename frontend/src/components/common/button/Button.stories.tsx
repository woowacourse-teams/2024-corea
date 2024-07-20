import Button from "./Button";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "common/Button",
  component: Button,
  parameters: {
    docs: {
      description: {
        component: "버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    variant: {
      description: "버튼 종류",
      control: { type: "select" },
      options: ["primary", "secondary", "disable"],
    },
    size: {
      description: "버튼 사이즈",
      control: { type: "select" },
      options: ["small", "medium", "large"],
    },
  },
} satisfies Meta<typeof Button>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    variant: "primary",
    size: "medium",
  },
  render: (args) => (
    <Button variant={args.variant} size={args.size}>
      기본 버튼
    </Button>
  ),
};

export const 버튼_종류_변경: Story = {
  args: {
    variant: "primary",
    size: "medium",
  },
  parameters: {
    docs: {
      description: {
        story: "버튼의 종류를 변경하는 스토리입니다.",
      },
    },
  },
  argTypes: {
    variant: {
      options: ["primary", "secondary", "disable"],
      control: { type: "radio" },
    },
  },
  render: (args) => (
    <Button variant={args.variant} size={args.size}>
      {args.variant}
    </Button>
  ),
};

export const 버튼_크기_변경: Story = {
  args: {
    variant: "primary",
    size: "large",
  },
  parameters: {
    docs: {
      description: {
        story: "버튼의 크기를 변경하는 스토리입니다.",
      },
    },
  },
  argTypes: {
    size: {
      options: ["small", "medium", "large"],
      control: { type: "radio" },
    },
  },
  render: (args) => (
    <Button variant={args.variant} size={args.size}>
      {args.size}
    </Button>
  ),
};
