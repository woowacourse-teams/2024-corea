import type { Meta, StoryObj } from "@storybook/react";
import { useState } from "react";
import Calendar from "@/components/common/calendar/Calendar";
import { CalendarDate } from "@/@types/date";

const meta = {
  title: "common/Calendar",
  component: Calendar,
  parameters: {
    docs: {
      description: {
        component: "달력 컴포넌트",
      },
    },
  },
  argTypes: {
    selectedDate: {
      description: "달력에 선택된 날짜",
    },
    handleSelectedDate: {
      description: "달력 날짜 선택 함수",
      action: "selected",
    },
  },
} satisfies Meta<typeof Calendar>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    selectedDate: {
      year: 9,
      month: 24,
      date: 15,
    },
    handleSelectedDate: () => {},
  },
  render: (args) => {
    const [selectedDate, setSelectedDate] = useState<CalendarDate>(args.selectedDate);

    const handleSelectedDate = (newSelectedDate: CalendarDate) => {
      setSelectedDate(newSelectedDate);
    };

    return <Calendar selectedDate={selectedDate} handleSelectedDate={handleSelectedDate} />;
  },
};
