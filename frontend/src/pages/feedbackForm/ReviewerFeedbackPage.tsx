import { useLocation, useParams } from "react-router-dom";
import { useFetchReviewerFeedback } from "@/hooks/queries/useFetchFeedback";
import { useFetchDetailRoomInfo } from "@/hooks/queries/useFetchRooms";
import ReviewerFeedbackLayout from "@/components/feedback/reviewerFeedbackLayout/ReviewerFeedbackLayout";
import { ReviewerInfo } from "@/@types/reviewer";

const ReviewerFeedbackPage = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  const roomId = Number(useParams().roomId);
  const username = queryParams.get("username") || "";
  const reviewer = location.state?.reviewer as ReviewerInfo;

  const { data: roomInfo } = useFetchDetailRoomInfo(roomId);
  const { data: feedbackData } = useFetchReviewerFeedback({
    roomId,
    username,
    enabled: reviewer.isWrited,
  });

  return (
    <ReviewerFeedbackLayout
      feedbackPageType={reviewer.isWrited ? "edit" : "create"}
      roomInfo={roomInfo}
      reviewer={reviewer}
      feedbackData={feedbackData}
    />
  );
};

export default ReviewerFeedbackPage;
