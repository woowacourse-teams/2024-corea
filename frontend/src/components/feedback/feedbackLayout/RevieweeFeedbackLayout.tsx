import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import Label from "@/components/common/label/Label";
import RevieweeFeedbackForm from "@/components/feedback/feedbackForm/RevieweeFeedbackForm";
import * as S from "@/components/feedback/feedbackLayout/FeedbackLayout.style";
import { RevieweeFeedbackData } from "@/@types/feedback";
import { ReviewInfo } from "@/@types/review";
import { RoomInfo } from "@/@types/roomInfo";
import { HoverStyledLink } from "@/styles/common";
import { theme } from "@/styles/theme";
import { FeedbackPageType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackProps {
  feedbackPageType: FeedbackPageType;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords">;
  reviewee?: ReviewInfo;
  feedbackData?: RevieweeFeedbackData;
}

const getInitialFormState = (
  reviewee?: ReviewInfo,
  feedbackData?: RevieweeFeedbackData,
): RevieweeFeedbackData => ({
  feedbackId: feedbackData?.feedbackId || 0,
  receiverId: feedbackData?.receiverId || reviewee?.userId || 0,
  evaluationPoint: feedbackData?.evaluationPoint || 0,
  feedbackKeywords: feedbackData?.feedbackKeywords || [],
  feedbackText: feedbackData?.feedbackText || "",
  recommendationPoint: feedbackData?.recommendationPoint || 0,
});

const RevieweeFeedbackLayout = ({
  feedbackPageType,
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

  const buttonText = feedbackPageType === "create" ? "작성하기" : "수정하기";

  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  const isFormValid =
    formState.evaluationPoint !== 0 &&
    formState.feedbackKeywords.length > 0 &&
    formState.recommendationPoint !== 0;

  useEffect(() => {
    if (feedbackData) {
      setFormState(getInitialFormState(reviewee, feedbackData));
    }
  }, [reviewee, feedbackData]);

  const handleChange = (
    key: keyof RevieweeFeedbackData,
    value: RevieweeFeedbackData[keyof RevieweeFeedbackData],
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
      postRevieweeFeedbackMutation.mutate(
        { feedbackData: formState },
        {
          onSuccess: () => {
            navigate(-1);
          },
        },
      );
    }

    if (feedbackPageType === "edit") {
      putRevieweeFeedbackMutation.mutate(
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
            <>리뷰이 {reviewee?.username} 피드백 </>
            {feedbackPageType === "create" && "작성하기"}
            {feedbackPageType === "edit" && "수정하기"}
            {feedbackPageType === "view" && "확인하기"}
          </S.PageType>

          <S.RoomInfoContainer>
            <S.RoomInfoWrapper>
              <S.PageTitle>{roomInfo.title}</S.PageTitle>
              <HoverStyledLink
                to={reviewee?.link || ""}
                target="_blank"
                rel="noreferrer"
                aria-label={`바로가기. 클릭하면 리뷰이의 PR 링크로 이동합니다.`}
              >
                <S.PRLink>
                  <S.IconWrapper>
                    <Icon kind="link" size="1.8rem" />
                  </S.IconWrapper>
                  PR 링크
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

        <RevieweeFeedbackForm
          isClicked={isClicked}
          formState={formState}
          onChange={handleChange}
          feedbackPageType={feedbackPageType}
        />

        <S.ButtonWrapper>
          <Button onClick={() => navigate(-1)} type="button" variant="primary" outline={true}>
            뒤로가기
          </Button>
          <Button onClick={handleSubmit}>{buttonText}</Button>
        </S.ButtonWrapper>
      </S.FeedbackContainer>
    </>
  );
};

export default RevieweeFeedbackLayout;
