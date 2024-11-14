import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import Label from "@/components/common/label/Label";
import ReviewerFeedbackForm from "@/components/feedback/feedbackForm/ReviewerFeedbackForm";
import * as S from "@/components/feedback/feedbackLayout/FeedbackLayout.style";
import { ReviewerFeedbackData } from "@/@types/feedback";
import { ReviewInfo } from "@/@types/review";
import { RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";
import { theme } from "@/styles/theme";
import { FeedbackPageType } from "@/utils/feedbackUtils";

interface ReviewerFeedbackProps {
  feedbackPageType: FeedbackPageType;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords">;
  reviewer?: ReviewInfo;
  feedbackData?: ReviewerFeedbackData;
}

const getInitialFormState = (
  reviewer?: ReviewInfo,
  feedbackData?: ReviewerFeedbackData,
): ReviewerFeedbackData => ({
  feedbackId: feedbackData?.feedbackId || 0,
  receiverId: feedbackData?.receiverId || reviewer?.userId || 0,
  evaluationPoint: feedbackData?.evaluationPoint || 0,
  feedbackKeywords: feedbackData?.feedbackKeywords || [],
  feedbackText: feedbackData?.feedbackText || "",
});

const ReviewerFeedbackLayout = ({
  feedbackPageType,
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

  const buttonText = feedbackPageType === "create" ? "작성하기" : "수정하기";

  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  const isFormValid = formState.evaluationPoint !== 0 && formState.feedbackKeywords.length > 0;

  useEffect(() => {
    if (feedbackData) {
      setFormState(getInitialFormState(reviewer, feedbackData));
    }
  }, [reviewer, feedbackData]);

  const handleChange = (
    key: keyof ReviewerFeedbackData,
    value: ReviewerFeedbackData[keyof ReviewerFeedbackData],
  ) => {
    if (feedbackPageType === "view") return;

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

    if (!isFormValid || feedbackPageType === "view") return;

    if (feedbackPageType === "create") {
      postReviewerFeedbackMutation.mutate(
        { feedbackData: formState },
        {
          onSuccess: () => {
            navigate(-1);
          },
        },
      );
    }

    if (feedbackPageType === "edit") {
      putReviewerFeedbackMutation.mutate(
        { feedbackId: formState.feedbackId, feedbackData: formState },
        {
          onSuccess: () => {
            navigate(-1);
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
            <>리뷰어 {reviewer?.username} 피드백 </>
            {feedbackPageType === "create" && "작성하기"}
            {feedbackPageType === "edit" && "수정하기"}
            {feedbackPageType === "view" && "확인하기"}
          </S.PageType>

          <S.RoomInfoContainer>
            <S.RoomInfoWrapper>
              <S.PageTitle>{roomInfo.title}</S.PageTitle>
              <HoverStyledLink
                to={reviewer?.link || ""}
                target="_blank"
                rel="noreferrer"
                aria-label={`바로가기. 클릭하면 리뷰어의 코멘트 링크로 이동합니다.`}
              >
                <S.PRLink>
                  <S.IconWrapper>
                    <Icon kind="link" size="1.8rem" />
                  </S.IconWrapper>
                  Comment 링크
                </S.PRLink>
              </HoverStyledLink>
            </S.RoomInfoWrapper>
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
          </S.RoomInfoContainer>
        </S.FeedbackContainerHeader>

        <ReviewerFeedbackForm
          isClicked={isClicked}
          formState={formState}
          onChange={handleChange}
          feedbackPageType={feedbackPageType}
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
