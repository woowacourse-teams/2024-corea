import type { Meta, StoryObj } from "@storybook/react";
import React from "react";
import KeywordOptionButton from "@/components/feedback/keywordOptionButton/KeywordOptionButton";

const meta = {
  title: "feedback/OptionButton",
  component: KeywordOptionButton,
  parameters: {
    docs: {
      description: {
        component: "피드백 관련 여러 옵션을 선택할 수 있는 버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    selectedEvaluationId: {
      control: { type: "number", min: 1, max: 5 },
      description: "선택된 평가 점수",
    },
    onChange: { action: "changed" },
  },
} satisfies Meta<typeof KeywordOptionButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
};

export const BadOptions: Story = {
  args: {
    selectedEvaluationId: 2,
  },
};

export const GoodOptions: Story = {
  args: {
    selectedEvaluationId: 4,
  },
};
