import { Textarea } from "./Textarea";
import type { Meta, StoryObj } from "@storybook/react";

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
    showCharCount: {
      control: {
        type: "boolean",
      },
      description: "글자수 보여주기 여부",
      defaultValue: false,
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
  render: (args) => (
    <Textarea maxLength={args.maxLength} value={`최대 ${args.maxLength}자 입력할 수 있습니다.`} />
  ),
};
