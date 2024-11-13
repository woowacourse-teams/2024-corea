import CalendarDropdown from "@/components/common/calendarDropdown/CalendarDropdown";
import { TimeDropdown } from "@/components/common/timeDropdown/TimeDropdown";

interface DateTimePickerProps {
  selectedDateTime: Date;
  onDateTimeChange: (dateTime: Date) => void;
  options: {
    disabledBeforeDate?: Date;
    isPastDateDisabled: boolean;
  };
}

const DateTimePicker = ({ selectedDateTime, options, onDateTimeChange }: DateTimePickerProps) => {
  const handleDateChange = (newDate: Date) => {
    const updatedDateTime = new Date(newDate);
    updatedDateTime.setHours(selectedDateTime.getHours());
    updatedDateTime.setMinutes(selectedDateTime.getMinutes());
    onDateTimeChange(updatedDateTime);
  };

  const handleTimeChange = (newTime: Date) => {
    const updatedDateTime = new Date(selectedDateTime);
    updatedDateTime.setHours(newTime.getHours());
    updatedDateTime.setMinutes(newTime.getMinutes());
    onDateTimeChange(updatedDateTime);
  };

  return (
    <>
      <CalendarDropdown
        selectedDate={selectedDateTime}
        handleSelectedDate={handleDateChange}
        options={options}
      />
      <TimeDropdown selectedTime={selectedDateTime} onTimeChange={handleTimeChange} />
    </>
  );
};

export default DateTimePicker;
