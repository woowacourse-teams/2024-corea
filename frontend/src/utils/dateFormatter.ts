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
  return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
};
