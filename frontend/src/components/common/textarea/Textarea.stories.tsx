import { Textarea } from "./Textarea";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "Common/Textarea",
  component: Textarea,
  argTypes: {
    error: {
      control: {
        type: "boolean",
      },
      description: "text error 여부",
      defaultValue: false,
    },
    placeholder: {
      control: {
        type: "text",
      },
      description: "placeholder",
      defaultValue: "여기에 입력해주세요.",
    },
    rows: {
      control: {
        type: "number",
      },
      description: "textarea 줄 수",
    },
    defaultValue: {
      control: {
        type: "text",
      },
      description: "textarea의 value",
    },
  },
} satisfies Meta<typeof Textarea>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    error: false,
    defaultValue: "기본 Textarea",
  },
};

export const 에러일_때: Story = {
  args: {
    error: true,
    placeholder: "필수 입력",
  },
};

export const Textarea_Row_변경: Story = {
  args: {
    error: false,
    rows: 10,
  },
  render: (args) => <Textarea error={args.error} rows={args.rows} value={`${args.rows}줄짜리`} />,
};
