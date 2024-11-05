// 피드백 작성/수정/확인 타입 구분
export type FeedbackPageType = "create" | "edit" | "view";

interface FeedbackPageTypeProps {
  isReviewed: boolean;
  isWrited: boolean;
  isClosed: boolean;
}

export interface FeedbackTypeResult {
  feedbackPageType: FeedbackPageType;
  buttonText: string;
}

export const getFeedbackPageType = ({
  isReviewed,
  isWrited,
  isClosed,
}: FeedbackPageTypeProps): FeedbackTypeResult => {
  if (!isReviewed) {
    return { feedbackPageType: "create", buttonText: "피드백 작성하기" };
  }

  // TODO: 방이 끝났을 때 피드백 렌더링 정하기
  // if (isClosed) {
  //   return { modalType: "view", buttonText: "피드백 확인하기" };
  // }

  if (isWrited) {
    return { feedbackPageType: "edit", buttonText: "피드백 수정하기" };
  }
  return { feedbackPageType: "create", buttonText: "피드백 작성하기" };
};
