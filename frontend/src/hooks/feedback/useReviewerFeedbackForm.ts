import { useEffect, useState } from "react";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import { useFetchReviewerFeedback } from "@/hooks/queries/useFetchFeedback";
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
  const { postReviewerFeedbackMutation, putReviewerFeedbackMutation } = useMutateFeedback();

  const { data: feedbackData } = useFetchReviewerFeedback({
    roomId,
    username: reviewerUsername,
    enabled: modalType !== "create",
  });

  useEffect(() => {
    if (modalType === "create") {
      setFormState({ ...initialFormState, receiverId });
    } else if (feedbackData) {
      setFormState({ ...feedbackData, receiverId, feedbackId: feedbackData.feedbackId });
    }
  }, [modalType, feedbackData, receiverId]);

  const handleChange = (
    key: keyof ReviewerFeedbackData,
    value: ReviewerFeedbackData[keyof ReviewerFeedbackData],
  ) => {
    if (modalType === "view") return;

    if (key === "evaluationPoint") {
      setFormState((prevState) => ({
        ...prevState,
        evaluationPoint: value as number,
        feedbackKeywords: [],
      }));
    } else {
      setFormState((prevState) => ({ ...prevState, [key]: value }));
    }
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
