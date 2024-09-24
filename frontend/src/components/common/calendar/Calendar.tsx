import * as S from "./Calendar.style";
import useCalendar from "@/hooks/common/useCalendar";
import Icon from "@/components/common/icon/Icon";
import { CalendarDate } from "@/@types/date";
import DAYS from "@/constants/days";

export interface CalendarProps {
  selectedDate: CalendarDate;
  handleSelectedDate: (newSelectedDate: CalendarDate) => void;
  options?: {
    isPastDateDisabled: boolean;
  };
}

const Calendar = ({ selectedDate, handleSelectedDate, options }: CalendarProps) => {
  const { currentDate, weekCalendarList, goToNextMonth, goToPreviousMonth } = useCalendar();

  const selectedYear = currentDate.getFullYear();
  const selectedMonth = currentDate.getMonth() + 1;

  const checkIsSelected = (date: number) => {
    if (
      selectedDate.year === selectedYear &&
      selectedDate.month === selectedMonth &&
      selectedDate.date === date
    ) {
      return true;
    }

    return false;
  };

  const handleClickCalendar = (date: number) => {
    const newSelectedDate: CalendarDate = {
      year: selectedYear,
      month: selectedMonth,
      date: date,
    };
    if (!checkIsAvailableClick(date)) return;

    handleSelectedDate(newSelectedDate);
  };

  const checkIsAvailableClick = (selectedDate: number) => {
    if (!options?.isPastDateDisabled) return true;

    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const checkDate = new Date(selectedYear, selectedMonth - 1, selectedDate);

    return today <= checkDate;
  };

  return (
    <S.CalendarContainer>
      <S.CalendarHeader>
        <S.CalendarSelectedDay>{`${selectedYear}년 ${selectedMonth}월`}</S.CalendarSelectedDay>
        <S.CalendarAdjustWrapper>
          <S.CalendarAdjustPrevious>
            <Icon kind="arrowLeft" size="2rem" onClick={goToPreviousMonth} />
          </S.CalendarAdjustPrevious>
          <S.CalendarAdjustNext>
            <Icon kind="arrowRight" size="2rem" onClick={goToNextMonth} />
          </S.CalendarAdjustNext>
        </S.CalendarAdjustWrapper>
      </S.CalendarHeader>
      <S.Table>
        <S.Thead>
          <S.Tr>
            {DAYS.map((day) => (
              <S.Th key={day}>{day}</S.Th>
            ))}
          </S.Tr>
        </S.Thead>
        <S.Tbody>
          {weekCalendarList.map((weeks, idx) => (
            <S.Tr key={idx}>
              {weeks.map((date, dateIdx) =>
                date !== 0 ? (
                  <S.Td
                    role="button"
                    key={dateIdx}
                    onClick={() => handleClickCalendar(date)}
                    $isSelected={checkIsSelected(date)}
                    $isAvailableClick={checkIsAvailableClick(date)}
                  >
                    {date}
                  </S.Td>
                ) : (
                  <td key={dateIdx} />
                ),
              )}
            </S.Tr>
          ))}
        </S.Tbody>
      </S.Table>
    </S.CalendarContainer>
  );
};

export default Calendar;
