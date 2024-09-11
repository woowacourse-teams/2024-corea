import type { Meta, StoryObj } from "@storybook/react";
import Dropdown from "@/components/common/dropdown/Dropdown";
import { dropdownItems } from "@/constants/roomDropdownItems";

const meta = {
  title: "MainPage/CategoryDropdown",
  component: Dropdown,
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
} satisfies Meta<typeof Dropdown>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    dropdownItems: dropdownItems,
    selectedCategory: "all",
    onSelectCategory: () => {},
  },
};
