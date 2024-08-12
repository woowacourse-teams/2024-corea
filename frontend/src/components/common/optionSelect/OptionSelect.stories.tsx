import OptionSelect from "./OptionSelect";
import type { Meta, StoryObj } from "@storybook/react";

const meta = {
  title: "feedback/OptionSelect",
  component: OptionSelect,
  parameters: {
    docs: {
      description: {
        score: "매너 온도 점수",
      },
    },
  },
  argTypes: {
    selected: {
      description: "현재 선택된 옵션",
    },
    options: {
      description: "사용할 옵션들",
    },
    handleSelectedOption: {
      description: "옵션 선택 핸들링 함수",
    },
  },
} satisfies Meta<typeof OptionSelect>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    selected: "쓴 피드백",
    options: ["쓴 피드백", "받은 피드백"],
    handleSelectedOption: () => {},
  },
};
