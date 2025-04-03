import BlurredFeedbackContent from "../BlurredFeedbackContent/BlurredFeedbackContent";
import FeedbackCardHeader from "../FeedbackCardHeader/FeedbackCardHeader";
import FeedbackContent from "../FeedbackContent/FeedbackContent";
import { useNavigate } from "react-router-dom";
import Button from "@/components/common/button/Button";
import * as S from "@/components/feedback/feedbackCard/FeedbackCard.style";
import { FeedbackCardData, FeedbackType } from "@/@types/feedback";
import { ReviewInfo } from "@/@types/review";

interface FeedbackCardProps {
  selectedFeedbackType: FeedbackType;
  feedbackCardData: FeedbackCardData;
  feedbackType: "develop" | "social";
}

const FeedbackCard = ({
  selectedFeedbackType,
  feedbackCardData,
  feedbackType,
}: FeedbackCardProps) => {
  const navigate = useNavigate();

  const reviewInfo: ReviewInfo = {
    userId: feedbackCardData.receiverId,
    username: feedbackCardData.username,
    link: feedbackCardData.link,
    isWrited: feedbackCardData.isWrited,
  };

  // [받은 피드백] 상대 피드백 작성 페이지 이동 함수
  const handleNavigateUserFeedbackPage = () => {
    navigate(
      `/rooms/${feedbackCardData.roomId}/feedback/${feedbackType === "develop" ? "reviewer" : "reviewee"}?username=${feedbackCardData.username}`,
      {
        state: { reviewInfo },
      },
    );
  };

  // [쓴 피드백] 내가 쓴 피드백 수정 페이지 이동 함수
  const handleNavigateMyFeedbackPage = () => {
    navigate(
      `/rooms/${feedbackCardData.roomId}/feedback/${feedbackType === "develop" ? "reviewee" : "reviewer"}?username=${feedbackCardData.username}`,
      {
        state: { reviewInfo },
      },
    );
  };

  return (
    <S.FeedbackCardContainer>
      <S.ScreenReader>미션의 상세 피드백 내용입니다.</S.ScreenReader>

      <S.FeedbackCardBox $isTypeDevelop={feedbackType === "develop"}>
        <FeedbackCardHeader
          selectedFeedbackType={selectedFeedbackType}
          feedbackCardData={feedbackCardData}
          feedbackType={feedbackType}
        />

        <S.FeedbackBody>
          <FeedbackContent feedbackCardData={feedbackCardData} feedbackType={feedbackType} />

          {!feedbackCardData.isWrited && (
            <BlurredFeedbackContent
              feedbackType={feedbackType}
              onClick={handleNavigateUserFeedbackPage}
            />
          )}

          {selectedFeedbackType === "쓴 피드백" && (
            <S.ButtonWrapper>
              <Button
                onClick={handleNavigateMyFeedbackPage}
                variant={feedbackType === "develop" ? "primary" : "secondary"}
                size="small"
              >
                수정하기
              </Button>
            </S.ButtonWrapper>
          )}
        </S.FeedbackBody>
      </S.FeedbackCardBox>
    </S.FeedbackCardContainer>
  );
};

export default FeedbackCard;
