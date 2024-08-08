import { useEffect, useState } from "react";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import { useFetchRevieweeFeedback } from "@/hooks/queries/useFetchFeedback";
import { ReviewerFeedbackData } from "@/@types/feedback";
import { FeedbackModalType } from "@/utils/feedbackUtils";

const initialFormState: ReviewerFeedbackData = {
  feedbackId: 0,
  receiverId: 0,
  evaluationPoint: 0,
  feedbackKeywords: [],
  feedbackText: "",
};

export const useReviewerFeedbackForm = (
  roomId: number,
  reviewerUsername: string,
  receiverId: number,
  modalType: FeedbackModalType,
  onClose: () => void,
) => {
  const [formState, setFormState] = useState<ReviewerFeedbackData>(initialFormState);
  const { data: feedbackData } = useFetchRevieweeFeedback(roomId, reviewerUsername);
  const { postReviewerFeedbackMutation, putReviewerFeedbackMutation } = useMutateFeedback();

  useEffect(() => {
    if (modalType === "create") {
      setFormState({ ...initialFormState, receiverId });
    } else if (feedbackData) {
      setFormState(feedbackData);
    }
  }, [modalType, feedbackData, receiverId]);

  const handleChange = (
    key: keyof ReviewerFeedbackData,
    value: ReviewerFeedbackData[keyof ReviewerFeedbackData],
  ) => {
    if (modalType === "view") return;
    setFormState((prevState) => ({ ...prevState, [key]: value }));
  };

  const isFormValid = formState.evaluationPoint !== 0 && formState.feedbackKeywords.length > 0;

  const handleSubmit = () => {
    if (!isFormValid || modalType === "view") return;

    const feedbackData = { ...formState };

    if (modalType === "create") {
      postReviewerFeedbackMutation.mutate(
        { roomId, feedbackData },
        {
          onSuccess: () => {
            onClose();
          },
        },
      );
    }

    if (modalType === "edit") {
      putReviewerFeedbackMutation.mutate(
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