// 피드백 작성/수정/확인 타입 구분
export type FeedbackModalType = "create" | "edit" | "view";

interface FeedbackTypeProps {
  isWrited: boolean;
  isClosed: boolean;
}

export interface FeedbackTypeResult {
  modalType: FeedbackModalType;
  buttonText: string;
}

export const getFeedbackType = ({ isWrited, isClosed }: FeedbackTypeProps): FeedbackTypeResult => {
  if (isClosed) {
    return { modalType: "view", buttonText: "피드백 확인" };
  }
  if (isWrited) {
    return { modalType: "edit", buttonText: "피드백 수정" };
  }
  return { modalType: "create", buttonText: "피드백 작성" };
};
