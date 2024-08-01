import Icon from "../icon/Icon";
import IconRadioButton from "./IconRadioButton";
import type { Meta, StoryObj } from "@storybook/react";
import React from "react";

const meta = {
  title: "common/IconRadioButton",
  component: IconRadioButton,
  parameters: {
    docs: {
      description: {
        component: "아이콘 라디오 버튼 컴포넌트",
      },
    },
  },
  argTypes: {
    text: {
      description: "아이콘 라디오 버튼 텍스트",
      control: { type: "text" },
    },
    name: {
      description: "라디오 버튼 그룹 이름",
      control: { type: "text" },
    },
    value: {
      description: "라디오 버튼 값",
      control: { type: "number" },
    },
    isSelected: {
      description: "라디오 버튼 선택 여부",
      control: { type: "boolean" },
    },
    onChange: {
      description: "선택 변경 시 호출되는 함수",
      action: "changed",
    },
  },
} satisfies Meta<typeof IconRadioButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    children: <Icon kind="thumbUp" />,
    text: "추천해요",
    name: "recommendationOption",
    value: 1,
    isSelected: false,
    onChange: () => {},
  },
};

export const Checked: Story = {
  args: {
    ...Default.args,
    isSelected: true,
  },
};
