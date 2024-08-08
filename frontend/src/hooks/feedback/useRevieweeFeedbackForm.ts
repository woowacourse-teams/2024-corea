import { useEffect, useState } from "react";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import { useFetchRevieweeFeedback } from "@/hooks/queries/useFetchFeedback";
import { RevieweeFeedbackData } from "@/@types/feedback";
import { FeedbackModalType } from "@/utils/feedbackUtils";

const initialFormState: RevieweeFeedbackData = {
  feedbackId: 0,
  receiverId: 0,
  evaluationPoint: 0,
  feedbackKeywords: [],
  feedbackText: "",
  recommendationPoint: 0,
};

export const useRevieweeFeedbackForm = (
  roomId: number,
  revieweeUsername: string,
  receiverId: number,
  modalType: FeedbackModalType,
  onClose: () => void,
) => {
  const [formState, setFormState] = useState<RevieweeFeedbackData>(initialFormState);
  const { data: feedbackData } = useFetchRevieweeFeedback(roomId, revieweeUsername);
  const { postRevieweeFeedbackMutation, putRevieweeFeedbackMutation } = useMutateFeedback();

  useEffect(() => {
    if (modalType === "create") {
      setFormState({ ...initialFormState, receiverId });
    } else if (feedbackData) {
      setFormState(feedbackData);
    }
  }, [modalType, feedbackData, receiverId]);

  const handleChange = (
    key: keyof RevieweeFeedbackData,
    value: RevieweeFeedbackData[keyof RevieweeFeedbackData],
  ) => {
    if (modalType === "view") return;
    setFormState((prevState) => ({ ...prevState, [key]: value }));
  };

  const isFormValid =
    formState.evaluationPoint !== 0 &&
    formState.feedbackKeywords.length > 0 &&
    formState.recommendationPoint !== 0;

  const handleSubmit = () => {
    if (!isFormValid || modalType === "view") return;

    const feedbackData = { ...formState };

    if (modalType === "create") {
      postRevieweeFeedbackMutation.mutate(
        { roomId, feedbackData },
        {
          onSuccess: () => {
            onClose();
          },
        },
      );
    }

    if (modalType === "edit") {
      putRevieweeFeedbackMutation.mutate(
        { roomId, feedbackId: feedbackData.feedbackId, feedbackData },
        {
          onSuccess: () => {
            onClose();
          },
        },
      );
    }
  };

  const handleClose = () => {
    setFormState(initialFormState);
    onClose();
  };

  return { formState, handleChange, isFormValid, handleSubmit, handleClose };
};
