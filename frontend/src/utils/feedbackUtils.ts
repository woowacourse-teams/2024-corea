// 피드백 작성/수정/확인 타입 구분
export type FeedbackType = "create" | "edit" | "view";

interface FeedbackTypeProps {
  isReviewed: boolean;
  isWrited: boolean;
  isClosed: boolean;
}

export interface FeedbackTypeResult {
  modalType: FeedbackType;
  buttonText: string;
}

export const getFeedbackType = ({
  isReviewed,
  isWrited,
  isClosed,
}: FeedbackTypeProps): FeedbackTypeResult => {
  if (!isReviewed) {
    return { modalType: "create", buttonText: "피드백 작성하기" };
  }
  // if (isClosed) {
  //   return { modalType: "view", buttonText: "피드백 확인하기" };
  // }
  if (isWrited) {
    return { modalType: "edit", buttonText: "피드백 수정하기" };
  }
  return { modalType: "create", buttonText: "피드백 작성하기" };
};
