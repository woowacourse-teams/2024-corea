import CalendarDropdown from "@/components/common/calendarDropdown/CalendarDropdown";
import { TimeDropdown } from "@/components/common/timeDropdown/TimeDropdown";

interface DateTimePickerProps {
  selectedDateTime: Date;
  onDateTimeChange: (dateTime: Date) => void;
  options: {
    disabledBeforeDate?: Date;
    isPastDateDisabled: boolean;
  };
  error: boolean;
}

const DateTimePicker = ({
  selectedDateTime,
  onDateTimeChange,
  options,
  error,
}: DateTimePickerProps) => {
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
    <div style={{ display: "flex", gap: "1rem" }}>
      <CalendarDropdown
        selectedDate={selectedDateTime}
        handleSelectedDate={handleDateChange}
        options={options}
        error={error}
      />
      <TimeDropdown selectedTime={selectedDateTime} onTimeChange={handleTimeChange} error={error} />
    </div>
  );
};

export default DateTimePicker;
