import * as S from "./CalendarDropdown.style";
import { InputHTMLAttributes } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import Calendar, { CalendarProps } from "@/components/common/calendar/Calendar";
import { formatDate } from "@/utils/dateFormatter";

type CalendarDropdownProps = CalendarProps &
  InputHTMLAttributes<HTMLInputElement> & { error: boolean };

const CalendarDropdown = ({
  selectedDate,
  handleSelectedDate,
  options,
  error,
  ...rest
}: CalendarDropdownProps) => {
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleDateChange = (newSelectedDate: Date) => {
    handleSelectedDate(newSelectedDate);

    handleToggleDropdown();
  };

  return (
    <S.CalendarDropdownContainer ref={dropdownRef}>
      <S.CalendarDropdownToggle
        type="text"
        value={formatDate(selectedDate)}
        onClick={handleToggleDropdown}
        placeholder="날짜를 선택하세요"
        $error={error}
        readOnly
        {...rest}
      />
      {isDropdownOpen && (
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
