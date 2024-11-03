import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import RevieweeFeedbackForm from "@/components/feedback/feedbackForm/RevieweeFeedbackForm";
import * as S from "@/components/feedback/revieweeFeedbackLayout/RevieweeFeedbackLayout.style";
import { RevieweeFeedbackData } from "@/@types/feedback";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";
import { FeedbackType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackProps {
  feedbackType: FeedbackType;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords">;
  reviewee: ReviewerInfo;
  feedbackData: RevieweeFeedbackData;
}

const getInitialFormState = (
  reviewee: ReviewerInfo,
  data: RevieweeFeedbackData,
): RevieweeFeedbackData => ({
  feedbackId: data.feedbackId || 0,
  receiverId: reviewee.userId,
  evaluationPoint: data.evaluationPoint || 0,
  feedbackKeywords: data.feedbackKeywords || [],
  feedbackText: data.feedbackText || "",
  recommendationPoint: data.recommendationPoint || 0,
});

const RevieweeFeedbackLayout = ({
  feedbackType,
  roomInfo,
  reviewee,
  feedbackData,
}: RevieweeFeedbackProps) => {
  const navigate = useNavigate();
  const [formState, setFormState] = useState<RevieweeFeedbackData>(() =>
    getInitialFormState(reviewee, feedbackData),
  );
  const { postRevieweeFeedbackMutation, putRevieweeFeedbackMutation } = useMutateFeedback(
    roomInfo.id,
  );
  const [isClicked, setIsClicked] = useState(false);

  const buttonText = feedbackType === "create" ? "작성하기" : "수정하기";

  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  useEffect(() => {
    if (feedbackType === "create") {
      setFormState({
        feedbackId: 0,
        receiverId: reviewee.userId,
        evaluationPoint: 0,
        feedbackKeywords: [],
        feedbackText: "",
        recommendationPoint: 0,
      });
    } else {
      setFormState(feedbackData);
    }
  }, [feedbackType, feedbackData, reviewee.userId]);

  const handleChange = (
    key: keyof RevieweeFeedbackData,
    value: RevieweeFeedbackData[keyof RevieweeFeedbackData],
  ) => {
    if (feedbackType === "view") return;

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
    setIsClicked(true);
    if (feedbackType === "view") return;

    const formattedFormState = {
      ...formState,
      feedbackText: formState.feedbackText,
      feedbackKeywords: formState.feedbackKeywords,
      evaluationPoint: formState.evaluationPoint,
    };

    if (feedbackType === "create") {
      postRevieweeFeedbackMutation.mutate(
        { feedbackData: formattedFormState },
        {
          onSuccess: () => {
            navigate(`/rooms/${roomInfo.id}`);
          },
        },
      );
    } else if (feedbackType === "edit") {
      putRevieweeFeedbackMutation.mutate(
        { feedbackId: formState.feedbackId, feedbackData: formattedFormState },
        {
          onSuccess: () => {
            navigate(`/rooms/${roomInfo.id}`);
          },
        },
      );
    }
  };

  return (
    <>
      <S.FeedbackContainer>
        <S.FeedbackContainerHeader>
          <S.PageType>
            <>{reviewee.username} </>
            {feedbackType === "create" && "리뷰이 피드백 작성하기"}
            {feedbackType === "edit" && "리뷰이 피드백 수정하기"}
            {feedbackType === "view" && "리뷰이 피드백 확인하기"}
          </S.PageType>
          <S.PageTitle>{roomInfo.title}</S.PageTitle>
          <S.Keywords>
            {displayedKeywords.length === 0 ? (
              <S.NoKeywordText>지정된 키워드 없음</S.NoKeywordText>
            ) : (
              displayedKeywords.map((keyword) => (
                <Label
                  key={keyword}
                  type="KEYWORD"
                  text={keyword}
                  size="semiSmall"
                  backgroundColor={theme.COLOR.grey0}
                />
              ))
            )}
          </S.Keywords>
        </S.FeedbackContainerHeader>

        <RevieweeFeedbackForm
          isClicked={isClicked}
          formState={formState}
          onChange={handleChange}
          feedbackType={feedbackType}
        />

        <S.ButtonWrapper>
          <Button onClick={() => navigate(-1)} type="button" variant="primary" outline={true}>
            뒤로가기
          </Button>
          <Button onClick={handleSubmit} disabled={!isFormValid}>
            {buttonText}
          </Button>
        </S.ButtonWrapper>
      </S.FeedbackContainer>
    </>
  );
};

export default RevieweeFeedbackLayout;
