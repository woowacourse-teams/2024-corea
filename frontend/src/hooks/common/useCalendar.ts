import { useState } from "react";

const CALENDER_LENGTH = 35;
const DAY_OF_WEEK = 7;
const DEFAULT_TRASH_VALUE = 0;

const useCalendar = () => {
  const [currentViewDate, setCurrentViewDate] = useState(new Date());

  const year = currentViewDate.getFullYear();
  const month = currentViewDate.getMonth();

  const firstDay = new Date(year, month, 1).getDay();
  const lastDate = new Date(year, month + 1, 0).getDate();

  const prevDayList = Array.from({ length: firstDay }, () => DEFAULT_TRASH_VALUE);
  const currentDayList = Array.from({ length: lastDate }, (_, idx) => idx + 1);
  const nextDayList = Array.from(
    { length: CALENDER_LENGTH - firstDay - lastDate },
    () => DEFAULT_TRASH_VALUE,
  );
  const currentCalendarList = [...prevDayList, ...currentDayList, ...nextDayList];

  const weekCalendarList = currentCalendarList.reduce((acc: number[][], cur, idx) => {
    const chunkIndex = Math.floor(idx / DAY_OF_WEEK);
    acc[chunkIndex] = acc[chunkIndex] || [];
    acc[chunkIndex].push(cur);
    return acc;
  }, []);

  const goToPreviousMonth = () => {
    setCurrentViewDate(new Date(year, month - 1, 1));
  };

  const goToNextMonth = () => {
    setCurrentViewDate(new Date(year, month + 1, 1));
  };

  return {
    weekCalendarList,
    currentViewDate,
    goToNextMonth,
    goToPreviousMonth,
  };
};

export default useCalendar;
