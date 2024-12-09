import * as S from "./Calendar.style";
import useCalendar from "@/hooks/common/useCalendar";
import Icon from "@/components/common/icon/Icon";
import { DAYS } from "@/constants/days";
import { areDatesEqual } from "@/utils/dateFormatter";

export interface CalendarProps {
  selectedDate: Date;
  handleSelectedDate: (newSelectedDate: Date) => void;
  options?: {
    disabledBeforeDate?: Date;
    isPastDateDisabled: boolean;
  };
}

const Calendar = ({ selectedDate, handleSelectedDate, options }: CalendarProps) => {
  const { currentViewDate, weekCalendarList, goToNextMonth, goToPreviousMonth } = useCalendar();

  const currentViewYear = currentViewDate.getFullYear();
  const currentViewMonth = currentViewDate.getMonth() + 1;

  const checkIsSelected = (day: number) => {
    const checkingDate = new Date(currentViewYear, currentViewMonth - 1, day);

    return areDatesEqual(selectedDate, checkingDate);
  };

  const handleClickCalendar = (day: number) => {
    if (!checkIsAvailableClick(day)) return;

    const newSelectedDate = new Date(currentViewYear, currentViewMonth - 1, day);

    handleSelectedDate(newSelectedDate);
  };

  const checkIsAvailableClick = (day: number) => {
    if (!options?.isPastDateDisabled) return true;

    const targetDate = new Date(options.disabledBeforeDate || new Date());
    targetDate.setHours(0, 0, 0, 0);

    const checkingDate = new Date(currentViewYear, currentViewMonth - 1, day);
    return targetDate <= checkingDate;
  };

  return (
    <S.CalendarContainer>
      <S.CalendarHeader>
        <S.CalendarSelectedDay>{`${currentViewYear}년 ${currentViewMonth}월`}</S.CalendarSelectedDay>
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
