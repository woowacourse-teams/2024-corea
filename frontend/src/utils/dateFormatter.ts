// 공통 포맷팅 함수
const formatDate = (
  dateString: string,
): { year: string; month: string; day: string; hours: string; minutes: string } => {
  const [datePart, timePart] = dateString.split("T");
  const [year, month, day] = datePart.split("-");
  const [hours, minutes] = timePart.split(":");

  return { year, month, day, hours, minutes };
};

// 마감일 포맷 함수
export const formatDeadlineString = (dateString: string): string => {
  const { year, month, day, hours, minutes } = formatDate(dateString);
  return `~ ${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분까지`;
};

// 일반 날짜 시간 포맷 함수
export const formatDateTimeString = (dateString: string): string => {
  const { year, month, day, hours, minutes } = formatDate(dateString);
  return `${year.slice(2)}-${month}-${day} ${hours}시 ${minutes}분`;
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
