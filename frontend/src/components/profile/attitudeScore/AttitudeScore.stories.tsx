import AttitudeScore from "./AttitudeScore";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "profile/AttitudeScore",
  component: AttitudeScore,
  parameters: {
    docs: {
      description: {
        score: "매너 온도 점수",
      },
    },
  },
  argTypes: {
    score: {
      description: "0에서 100 사이의 점수",
      control: { type: "range", min: 0, max: 100 },
    },
  },
} satisfies Meta<typeof AttitudeScore>;

export default meta;

type Story = StoryObj<typeof meta>;

export const 점수_없음: Story = {
  args: {
    score: 0,
  },
};

export const 오십점: Story = {
  args: {
    score: 50,
  },
};

export const 백점: Story = {
  args: {
    score: 100,
  },
};
