export const formatDateString = (dateString: string): string => {
  const [datePart, timePart] = dateString.split("T");
  const [year, month, day] = datePart.split("-");
  const [hours, minutes] = timePart.split(":");

  return `~ ${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분까지`;
};
