import type { Meta, StoryObj } from "@storybook/react";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";

const meta = {
  title: "Feedback/EvaluationPointBar",
  component: EvaluationPointBar,
  parameters: {
    docs: {
      description: {
        component: "점수 라디오 버튼 바 컴포넌트",
      },
    },
  },
} satisfies Meta<typeof EvaluationPointBar>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {};
