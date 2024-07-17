import Icon from "./Icon";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "common/Icon",
  component: Icon,
  parameters: {
    docs: {
      description: {
        component: "아이콘 컴포넌트",
      },
    },
  },
  argTypes: {
    kind: {
      description: "아이콘 종류",
    },
    onClick: {
      description: "아이콘 클릭 이벤트",
    },
    color: {
      description: "아이콘 색상",
      control: { type: "radio" },
      options: ["black", "red", "green"],
    },
    size: {
      description: "아이콘 크기",
    },
  },
} satisfies Meta<typeof Icon>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    kind: "person",
    color: "black",
  },
};
