import SearchBar from "./SearchBar";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "Common/SearchBar",
  component: SearchBar,
  argTypes: {
    placeholder: {
      control: {
        type: "text",
      },
      description: "placeholder",
      defaultValue: "방 제목을 검색해주세요",
    },
    defaultValue: {
      control: {
        type: "text",
      },
      description: "textarea의 value",
    },
  },
} satisfies Meta<typeof SearchBar>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    value: "기본 입력창",
    handleValue: () => {},
    handleSearch: () => {},
  },
};
