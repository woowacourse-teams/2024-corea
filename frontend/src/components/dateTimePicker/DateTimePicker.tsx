import CalendarDropdown from "@/components/common/calendarDropdown/CalendarDropdown";
import { TimeDropdown } from "@/components/common/timeDropdown/TimeDropdown";

interface DateTimePickerProps {
  selectedDateTime: Date;
  onDateTimeChange: (dateTime: Date) => void;
}

const DateTimePicker = ({ selectedDateTime, onDateTimeChange }: DateTimePickerProps) => {
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
      <CalendarDropdown selectedDate={selectedDateTime} handleSelectedDate={handleDateChange} />
      <TimeDropdown selectedTime={selectedDateTime} onTimeChange={handleTimeChange} />
    </>
  );
};

export default DateTimePicker;
