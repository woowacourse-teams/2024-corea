import { useLocation, useParams } from "react-router-dom";
import { useFetchRevieweeFeedback } from "@/hooks/queries/useFetchFeedback";
import { useFetchDetailRoomInfo } from "@/hooks/queries/useFetchRooms";
import RevieweeFeedbackLayout from "@/components/feedback/revieweeFeedbackLayout/RevieweeFeedbackLayout";
import { RevieweeFeedbackData } from "@/@types/feedback";
import { ReviewerInfo } from "@/@types/reviewer";

const RevieweeFeedbackPage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const roomId = Number(useParams().roomId);
  const username = queryParams.get("username") || "";
  const reviewee = location.state?.reviewee as ReviewerInfo;

  const { data: roomInfo } = useFetchDetailRoomInfo(roomId);
  const { data: feedbackData } = useFetchRevieweeFeedback({
    roomId,
    username,
  });

  const initialFeedbackData: RevieweeFeedbackData = {
    feedbackId: 0,
    receiverId: 0,
    evaluationPoint: 0,
    feedbackKeywords: [],
    feedbackText: "",
    recommendationPoint: 0,
  };

  return (
    <RevieweeFeedbackLayout
      feedbackType={feedbackData ? "edit" : "create"}
      roomInfo={roomInfo}
      reviewee={reviewee}
      feedbackData={feedbackData || initialFeedbackData}
    />
  );
};

export default RevieweeFeedbackPage;
