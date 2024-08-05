// 피드백 작성/수정/확인 타입 구분
interface getFeedbackModalTypeProps {
  isWrited: boolean;
  isClosed: boolean;
}
export const getFeedbackModalType = ({ isWrited, isClosed }: getFeedbackModalTypeProps) => {
  if (isClosed) return "피드백 확인";
  return isWrited ? "피드백 수정" : "피드백 작성";
};
