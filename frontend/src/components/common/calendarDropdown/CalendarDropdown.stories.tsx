import type { Meta, StoryObj } from "@storybook/react";
import { useState } from "react";
import CalendarDropdown from "@/components/common/calendarDropdown/CalendarDropdown";

const meta = {
  title: "common/CalendarDropdown",
  component: CalendarDropdown,
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
} satisfies Meta<typeof CalendarDropdown>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    selectedDate: new Date(),
    handleSelectedDate: () => {},
  },
  render: (args) => {
    const [selectedDate, setSelectedDate] = useState<Date>(args.selectedDate);

    const handleSelectedDate = (newSelectedDate: Date) => {
      setSelectedDate(newSelectedDate);
    };

    return <CalendarDropdown selectedDate={selectedDate} handleSelectedDate={handleSelectedDate} />;
  },
};

export const 이전날짜_선택_불가_캘린더: Story = {
  args: {
    selectedDate: new Date(),
    handleSelectedDate: () => {},
    options: {
      isPastDateDisabled: true,
    },
  },
  render: (args) => {
    const [selectedDate, setSelectedDate] = useState<Date>(args.selectedDate);

    const handleSelectedDate = (newSelectedDate: Date) => {
      setSelectedDate(newSelectedDate);
    };

    return (
      <CalendarDropdown
        selectedDate={selectedDate}
        handleSelectedDate={handleSelectedDate}
        options={args.options}
      />
    );
  },
};

export const 캘린더_드롭다운_에러: Story = {
  args: {
    selectedDate: new Date(),
    handleSelectedDate: () => {},
    options: {
      isPastDateDisabled: true,
    },
  },
  render: (args) => {
    const [selectedDate, setSelectedDate] = useState<Date>(args.selectedDate);

    const handleSelectedDate = (newSelectedDate: Date) => {
      setSelectedDate(newSelectedDate);
    };

    return (
      <CalendarDropdown
        selectedDate={selectedDate}
        handleSelectedDate={handleSelectedDate}
        options={args.options}
      />
    );
  },
};
