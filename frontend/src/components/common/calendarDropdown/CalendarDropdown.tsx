import * as S from "./CalendarDropdown.style";
import { InputHTMLAttributes } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import Calendar, { CalendarProps } from "@/components/common/calendar/Calendar";
import { CalendarDate } from "@/@types/date";

type CalendarDropdownProps = CalendarProps &
  InputHTMLAttributes<HTMLInputElement> & {
    error?: boolean;
  };

const CalendarDropdown = ({
  selectedDate,
  handleSelectedDate,
  options,
  error = false,
  ...rest
}: CalendarDropdownProps) => {
  const { isOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleDateChange = (newSelectedDate: CalendarDate) => {
    handleSelectedDate(newSelectedDate);

    handleToggleDropdown();
  };

  return (
    <S.CalendarDropdownContainer ref={dropdownRef}>
      <S.CalendarDropdownToggle
        type="text"
        value={`${selectedDate.year}-${selectedDate.month}-${selectedDate.date}`}
        onClick={handleToggleDropdown}
        placeholder="날짜를 선택하세요"
        readOnly
        $error={error}
        {...rest}
      />
      {isOpen && (
        <Calendar
          selectedDate={selectedDate}
          handleSelectedDate={handleDateChange}
          options={options}
        />
      )}
    </S.CalendarDropdownContainer>
  );
};

export default CalendarDropdown;
