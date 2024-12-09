import { TimeDropdown } from "./TimeDropdown";
import type { Meta, StoryObj } from "@storybook/react";
import { useState } from "react";

const meta = {
  title: "Common/TimeDropdown",
  component: TimeDropdown,
  argTypes: {
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
    error: {
      control: {
        type: "boolean",
      },
    },
  },
} satisfies Meta<typeof TimeDropdown>;

export default meta;

type Story = StoryObj<typeof meta>;

// 기본 스토리 (Default)
export const Default: Story = {
  args: {
    selectedTime: new Date(),
    onTimeChange: () => {},
    error: false,
  },
  render: (args) => {
    const [time, setTime] = useState<Date>(args.selectedTime);
    return (
      <TimeDropdown
        selectedTime={time}
        onTimeChange={(newTime) => {
          setTime(newTime);
          args.onTimeChange(newTime);
        }}
        error={false}
      />
    );
  },
};
