import Button from "./Checkbox";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "common/Checkbox",
  component: Button,
  parameters: {
    docs: {
      description: {
        component: "체크박스 컴포넌트",
      },
    },
  },
  argTypes: {
    id: {
      description: "체크박스 id",
    },
    label: {
      description: "체크박스 설명",
    },
    checked: {
      description: "체크박스 체크 여부",
      control: { type: "boolean" },
    },
    onChange: {
      description: "체크박스 체크 핸들러",
    },
  },
} satisfies Meta<typeof Button>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    id: "checkbox-storybook",
    label: "체크해주세요",
    checked: false,
    onChange: () => {},
  },
};

export const 체크된_체크박스: Story = {
  args: {
    id: "checkbox-storybook",
    label: "체크해주세요",
    checked: true,
    onChange: () => {},
  },
};
