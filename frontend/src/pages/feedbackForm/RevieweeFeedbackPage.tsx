import { useLocation, useParams } from "react-router-dom";
import { useFetchRevieweeFeedback } from "@/hooks/queries/useFetchFeedback";
import { useFetchDetailRoomInfo } from "@/hooks/queries/useFetchRooms";
import RevieweeFeedbackLayout from "@/components/feedback/feedbackLayout/RevieweeFeedbackLayout";
import { ReviewerInfo } from "@/@types/reviewer";

const RevieweeFeedbackPage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const roomId = Number(useParams().roomId);
  const username = queryParams.get("username") || "";
  const reviewee = location.state?.revieweInfo as ReviewerInfo;

  const { data: roomInfo } = useFetchDetailRoomInfo(roomId);
  const { data: feedbackData } = useFetchRevieweeFeedback({
    roomId,
    username,
    enabled: reviewee.isWrited,
  });

  return (
    <RevieweeFeedbackLayout
      feedbackPageType={reviewee.isWrited ? "edit" : "create"}
      roomInfo={roomInfo}
      reviewee={reviewee}
      feedbackData={feedbackData}
    />
  );
};

export default RevieweeFeedbackPage;
