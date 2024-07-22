import IconButton from "./IconButton";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "common/IconButton",
  component: IconButton,
  parameters: {
    docs: {
      description: {
        component: "아이콘 버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    iconKind: {
      description: "아이콘 종류",
      control: { type: "select" },
      options: ["person", "link", "calendar"],
    },
    text: {
      description: "아이콘 버튼 텍스트",
      control: { type: "text" },
    },
  },
} satisfies Meta<typeof IconButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    iconKind: "person",
    text: "person",
  },
};

export const WithoutText: Story = {
  args: {
    iconKind: "link",
  },
};

export const WithDifferentIcon: Story = {
  args: {
    iconKind: "calendar",
    text: "calendar",
  },
};
