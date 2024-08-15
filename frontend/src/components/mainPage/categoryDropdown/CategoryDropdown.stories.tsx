import CategoryDropdown from "./CategoryDropdown";
import type { Meta, StoryObj } from "@storybook/react";
import { useState } from "react";
import { allLogo, androidLogo, backendLogo, frontendLogo } from "@/assets";

const meta = {
  title: "MainPage/CategoryDropdown",
  component: CategoryDropdown,
  parameters: {
    docs: {
      description: {
        component: "카테고리 선택을 위한 드롭다운 컴포넌트",
      },
    },
  },
  argTypes: {
    selectedCategory: {
      description: "현재 선택된 카테고리",
      control: "select",
      options: ["all", "an", "be", "fe"],
    },
    onSelectCategory: {
      description: "카테고리 선택 핸들링 함수",
      action: "selected",
    },
  },
} satisfies Meta<typeof CategoryDropdown>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    selectedCategory: "all",
    onSelectCategory: () => {},
  },
};
