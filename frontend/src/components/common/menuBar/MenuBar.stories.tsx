import MenuBar from "./MenuBar";
import { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "common/MenuBar",
  component: MenuBar,
  parameters: {
    docs: {
      description: {
        component: "카테고리 선택을 위한 메뉴바 컴포넌트",
      },
    },
  },
  argTypes: {
    selectedCategory: {
      control: "select",
      options: ["all", "an", "be", "fe"],
    },
    onCategoryClick: { action: "clicked" },
  },
} satisfies Meta<typeof MenuBar>;

export default meta;

type Story = StoryObj<typeof meta>;

const defaultArgs = {
  onCategoryClick: (category: string) => {},
};

export const Default: Story = {
  args: {
    ...defaultArgs,
    selectedCategory: "all",
  },
};

export const AndroidSelected: Story = {
  args: {
    ...defaultArgs,
    selectedCategory: "an",
  },
};

export const SmallViewport: Story = {
  parameters: {
    viewport: {
      defaultViewport: "mobile1",
    },
  },
  args: {
    ...defaultArgs,
    selectedCategory: "all",
  },
};
