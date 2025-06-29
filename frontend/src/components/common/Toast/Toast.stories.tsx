import Toast from "./Toast";
import type { Meta, StoryObj } from "@storybook/react";
import useToast from "@/hooks/common/useToast";

const meta = {
  title: "common/Toast",
  component: Toast,
  parameters: {
    docs: {
      description: {
        component: "토스트 컴포넌트",
      },
    },
  },

  decorators: [
    (Story, context) => {
      const { showToast } = useToast();

      return (
        <div>
          <Toast />
          <button onClick={() => showToast("나는 토스트", "error")}>토스트 열기</button>
          <Story />
        </div>
      );
    },
  ],
} satisfies Meta<typeof Toast>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {};
