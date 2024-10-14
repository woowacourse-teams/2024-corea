// 공통 포맷팅 함수
const formatStringToDate = (
  dateString: string,
): { year: string; month: string; day: string; hours: string; minutes: string } => {
  const [datePart, timePart] = dateString.split("T");
  const [year, month, day] = datePart.split("-");
  const [hours, minutes] = timePart.split(":");

  return { year, month, day, hours, minutes };
};

// 마감일 포맷 함수
export const formatDeadlineString = (dateString: string): string => {
  const { year, month, day, hours, minutes } = formatStringToDate(dateString);
  return `${year.slice(2)}-${month}-${day} ${hours}:${minutes}`;
};

// 일반 날짜 시간 포맷 함수
export const formatDateTimeString = (dateString: string): string => {
  const { year, month, day, hours, minutes } = formatStringToDate(dateString);
  return `${year.slice(2)}-${month}-${day} ${hours}:${minutes}`;
};

// 디데이 포맷 함수
export const formatDday = (dateString: string): string => {
  const targetDate = new Date(dateString);
  const now = new Date();

  const timeDiff = targetDate.getTime() - now.getTime();
  const dayDiff = Math.floor(timeDiff / (1000 * 3600 * 24));

  const hoursDiff = timeDiff / (1000 * 3600);

  if (dayDiff > 0 && hoursDiff >= 24) {
    return `D-${dayDiff}`;
  }
  if (dayDiff >= 0 && hoursDiff < 24) {
    return "D-Day";
  }
  return "종료됨";
};

// 날짜 -> 문자열
export const formatDate = (date: Date): string => {
  const year = date.getFullYear().toString();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  return `${year}-${month}-${day}`;
};

// 시간 -> 문자열
export const formatTime = (time: Date): string => {
  const hour = time.getHours().toString().padStart(2, "0");
  const minutes = time.getMinutes().toString().padStart(2, "0");
  return `${hour}:${minutes}`;
};

// 날짜+시간
export const formatCombinedDateTime = (date: Date, time: Date): string => {
  return `${formatDate(date)} ${formatTime(time)}`;
};

export const formatLeftTime = (time: string) => {
  const today = formatTime(new Date());
  const [currentHours, currentMinutes] = today.split(":");
  const { hours: targetHour, minutes: targetMinutes } = formatStringToDate(time);

  const { hours, minutes } = calculateTimeDifference(
    currentHours,
    currentMinutes,
    targetHour,
    targetMinutes,
  );

  if (hours === 0 && minutes === 0) return "곧 종료";

  return hours !== 0 ? `${hours}시간 ${minutes}분 전` : `${minutes}분 전`;
};

export const areDatesEqual = (date1: Date, date2: Date): boolean => {
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
};

const calculateTimeDifference = (
  currentHourStr: string,
  currentMinuteStr: string,
  targetHourStr: string,
  targetMinuteStr: string,
) => {
  const currentHour = parseInt(currentHourStr, 10);
  const currentMinute = parseInt(currentMinuteStr, 10);
  const targetHour = parseInt(targetHourStr, 10);
  const targetMinute = parseInt(targetMinuteStr, 10);

  const currentTotalMinutes = currentHour * 60 + currentMinute;
  const targetTotalMinutes = targetHour * 60 + targetMinute;

  let diffMinutes = targetTotalMinutes - currentTotalMinutes;

  if (diffMinutes < 0) {
    diffMinutes += 24 * 60; // 24시간 = 1440분
  }

  const diffHours = Math.floor(diffMinutes / 60);
  const remainingMinutes = diffMinutes % 60;

  return {
    hours: diffHours,
    minutes: remainingMinutes,
  };
};

export const displayLeftTime = (time: string) => {
  const Dday = formatDday(time);
  return Dday === "D-Day" ? formatLeftTime(time) : Dday;
};
