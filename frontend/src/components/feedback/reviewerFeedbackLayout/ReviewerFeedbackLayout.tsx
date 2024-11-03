import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import ReviewerFeedbackForm from "@/components/feedback/feedbackForm/ReviewerFeedbackForm";
import * as S from "@/components/feedback/reviewerFeedbackLayout/ReviewerFeedbackLayout.style";
import { ReviewerFeedbackData } from "@/@types/feedback";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";
import { FeedbackType } from "@/utils/feedbackUtils";

interface ReviewerFeedbackProps {
  feedbackType: FeedbackType;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords">;
  reviewer: ReviewerInfo;
  feedbackData: ReviewerFeedbackData;
}

const getInitialFormState = (
  reviewer: ReviewerInfo,
  data: ReviewerFeedbackData,
): ReviewerFeedbackData => ({
  feedbackId: data.feedbackId || 0,
  receiverId: reviewer.userId,
  evaluationPoint: data.evaluationPoint || 0,
  feedbackKeywords: data.feedbackKeywords || [],
  feedbackText: data.feedbackText || "",
});

const ReviewerFeedbackLayout = ({
  feedbackType,
  roomInfo,
  reviewer,
  feedbackData,
}: ReviewerFeedbackProps) => {
  const navigate = useNavigate();
  const { postReviewerFeedbackMutation, putReviewerFeedbackMutation } = useMutateFeedback(
    roomInfo.id,
  );
  const [formState, setFormState] = useState<ReviewerFeedbackData>(() =>
    getInitialFormState(reviewer, feedbackData),
  );
  const [isClicked, setIsClicked] = useState(false);

  const buttonText = feedbackType === "create" ? "작성하기" : "수정하기";

  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  useEffect(() => {
    if (feedbackType === "create") {
      setFormState({
        feedbackId: 0,
        receiverId: reviewer.userId,
        evaluationPoint: 0,
        feedbackKeywords: [],
        feedbackText: "",
      });
    } else {
      setFormState(feedbackData);
    }
  }, [feedbackType, feedbackData, reviewer.userId]);

  const handleChange = (
    key: keyof ReviewerFeedbackData,
    value: ReviewerFeedbackData[keyof ReviewerFeedbackData],
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
      postReviewerFeedbackMutation.mutate(
        { feedbackData: formattedFormState },
        {
          onSuccess: () => {
            navigate(`/rooms/${roomInfo.id}`);
          },
        },
      );
    } else if (feedbackType === "edit") {
      putReviewerFeedbackMutation.mutate(
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
            <>{reviewer.username} </>
            {feedbackType === "create" && "리뷰어 피드백 작성하기"}
            {feedbackType === "edit" && "리뷰어 피드백 수정하기"}
            {feedbackType === "view" && "리뷰어 피드백 확인하기"}
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

        <ReviewerFeedbackForm
          isClicked={isClicked}
          formState={formState}
          onChange={handleChange}
          feedbackType={feedbackType}
        />

        <S.ButtonWrapper>
          <Button onClick={() => navigate(-1)} type="button" variant="secondary" outline={true}>
            뒤로가기
          </Button>
          <Button onClick={handleSubmit} variant="secondary">
            {buttonText}
          </Button>
        </S.ButtonWrapper>
      </S.FeedbackContainer>
    </>
  );
};

export default ReviewerFeedbackLayout;
