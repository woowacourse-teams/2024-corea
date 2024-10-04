import { Input } from "./Input";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "Common/Input",
  component: Input,
  argTypes: {
    error: {
      control: {
        type: "boolean",
      },
      description: "text error 여부",
      defaultValue: false,
    },
    showCharCount: {
      control: {
        type: "boolean",
      },
      description: "글자수 보여주기 여부",
      defaultValue: false,
    },
  },
} satisfies Meta<typeof Input>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    error: false,
    defaultValue: "기본 Input",
  },
};

export const 에러일_때: Story = {
  args: {
    error: true,
    placeholder: "필수 입력",
  },
};

export const 글자수_보여주기: Story = {
  args: {
    showCharCount: true,
  },
};

export const 최대_글자수_보여주기: Story = {
  args: {
    showCharCount: true,
    maxLength: 10,
  },
};
