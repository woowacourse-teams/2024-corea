import CalendarDropdown from "@/components/common/calendarDropdown/CalendarDropdown";
import { TimeDropdown } from "@/components/common/timeDropdown/TimeDropdown";

interface DateTimePickerProps {
  selectedDateTime: Date;
  onDateTimeChange: (dateTime: Date) => void;
  error: boolean;
}

const DateTimePicker = ({ selectedDateTime, onDateTimeChange, error }: DateTimePickerProps) => {
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
        error={error}
      />
      <TimeDropdown selectedTime={selectedDateTime} onTimeChange={handleTimeChange} error={error} />
    </div>
  );
};

export default DateTimePicker;
