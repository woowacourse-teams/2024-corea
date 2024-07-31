import Profile from "./Profile";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "common/Profile",
  component: Profile,
  parameters: {
    docs: {
      description: {
        component: "깃허브 프로필 이미지",
      },
    },
  },
  argTypes: {
    imgSrc: { description: "프로필 이미지 주소 링크" },
  },
} satisfies Meta<typeof Profile>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    imgSrc: "https://avatars.githubusercontent.com/u/63334368?v=4",
  },
};
