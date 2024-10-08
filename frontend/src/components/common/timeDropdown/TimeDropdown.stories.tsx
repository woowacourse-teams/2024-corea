import { TimeDropdown } from "./TimeDropdown";
import type { Meta, StoryObj } from "@storybook/react";
import { useState } from "react";

const meta = {
  title: "Common/TimeDropdown",
  component: TimeDropdown,
  argTypes: {
    error: {
      control: {
        type: "boolean",
      },
      description: "시간 입력 오류 여부",
      defaultValue: false,
    },
    selectedTime: {
      control: {
        type: "object",
      },
      description: "초기 시간 값 (hour, minute)",
      defaultValue: {
        hour: new Date().getHours(),
        minute: new Date().getMinutes(),
      },
    },
    onTimeChange: {
      description: "시간 변경 시 호출되는 콜백",
      action: "changed",
    },
  },
} satisfies Meta<typeof TimeDropdown>;

export default meta;

type Story = StoryObj<typeof meta>;

// 기본 스토리 (Default)
export const Default: Story = {
  args: {
    error: false,
    selectedTime: new Date(),
    onTimeChange: () => {},
  },
  render: (args) => {
    const [time, setTime] = useState<Date>(args.selectedTime);
    return (
      <TimeDropdown
        error={args.error}
        selectedTime={time}
        onTimeChange={(newTime) => {
          setTime(newTime);
          args.onTimeChange(newTime);
        }}
      />
    );
  },
};

// 에러 발생 시
export const 에러일_때: Story = {
  args: {
    error: true,
    selectedTime: new Date(),
    onTimeChange: () => {},
  },
  render: (args) => {
    const [time, setTime] = useState<Date>(args.selectedTime);
    return (
      <TimeDropdown
        error={args.error}
        selectedTime={time}
        onTimeChange={(newTime) => {
          setTime(newTime);
          args.onTimeChange(newTime);
        }}
      />
    );
  },
};
