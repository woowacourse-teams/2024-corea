import type { Meta, StoryObj } from "@storybook/react";
import React from "react";
import RecommendationPointBar from "@/components/common/recommendationPointBar/RecommendationPointBar";

const meta = {
  title: "common/RecommendationPointBar",
  component: RecommendationPointBar,
  parameters: {
    docs: {
      description: {
        component: "추천/비추천 라디오 버튼 바 컴포넌트",
      },
    },
  },
} satisfies Meta<typeof RecommendationPointBar>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {};
