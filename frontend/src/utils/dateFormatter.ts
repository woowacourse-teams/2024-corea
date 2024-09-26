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
  const today = new Date();

  today.setHours(0, 0, 0, 0);
  targetDate.setHours(0, 0, 0, 0);

  const timeDiff = targetDate.getTime() - today.getTime();
  const dayDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

  if (dayDiff > 0) {
    return `D-${dayDiff}`;
  }
  if (dayDiff === 0) {
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
