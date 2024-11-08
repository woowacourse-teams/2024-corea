import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useMutateReviewComplete from "@/hooks/mutations/useMutateReview";
import { useFetchReviewee } from "@/hooks/queries/useFetchReviewee";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/myReviewee/MyReviewee.style";
import { ReviewInfo } from "@/@types/review";
import { RoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { HoverStyledLink } from "@/styles/common";
import { getFeedbackPageType } from "@/utils/feedbackUtils";

interface MyRevieweeProps {
  roomInfo: RoomInfo;
}

const MyReviewee = ({ roomInfo }: MyRevieweeProps) => {
  const navigate = useNavigate();
  const { data: revieweeData } = useFetchReviewee(roomInfo);
  const { postReviewCompleteMutation } = useMutateReviewComplete(roomInfo.id);
  const [loadingButtonId, setLoadingButtonId] = useState<number[]>([]);

  // 피드백 페이지 이동 함수
  const handleNavigateFeedbackPage = (reviewInfo: ReviewInfo) => {
    navigate(`/rooms/${roomInfo.id}/feedback/reviewee?username=${reviewInfo.username}`, {
      state: { reviewInfo },
    });
  };

  // 코드 리뷰 완료 post 요청 보내는 함수
  const handleReviewCompleteClick = (reviewee: ReviewInfo) => {
    if (loadingButtonId.includes(reviewee.userId)) return;
    setLoadingButtonId((prev) => [...prev, reviewee.userId]);

    postReviewCompleteMutation.mutate(
      { roomId: roomInfo.id, revieweeId: reviewee.userId },
      {
        onSuccess: () => {
          handleNavigateFeedbackPage(reviewee);
          setLoadingButtonId((prev) => prev.filter((id) => id !== reviewee.userId));
        },
        onError: () => setLoadingButtonId((prev) => prev.filter((id) => id !== reviewee.userId)),
      },
    );
  };

  // 리뷰 및 피드백 여부 버튼 렌더링 함수
  const renderRevieweeButton = (reviewee: ReviewInfo) => {
    const { buttonText } = getFeedbackPageType({
      isReviewed: reviewee.isReviewed ?? true,
      isWrited: reviewee.isWrited,
      isClosed: roomInfo.roomStatus === "CLOSE",
    });

    if (roomInfo.roomStatus === "CLOSE" && !reviewee.isReviewed) {
      return <p>코드리뷰를 하지 않았어요</p>;
    }

    // TODO: 방이 끝났을 때 피드백 렌더링 정하기
    // if (roomInfo.roomStatus === "CLOSE" && !reviewee.isWrited) {
    //   return <p>피드백을 작성하지 않았어요</p>;
    // }

    return reviewee.isReviewed ? (
      <Button
        size="xSmall"
        variant="primary"
        onClick={() => handleNavigateFeedbackPage(reviewee)}
        disabled={!reviewee.isReviewed}
        isLoading={loadingButtonId.includes(reviewee.userId)}
      >
        {buttonText}
      </Button>
    ) : (
      <Button
        size="xSmall"
        variant="confirm"
        disabled={reviewee.isReviewed}
        onClick={() => handleReviewCompleteClick(reviewee)}
        isLoading={loadingButtonId.includes(reviewee.userId)}
      >
        코드리뷰 마치기
      </Button>
    );
  };

  // 매칭 전 보여줄 화면
  if (roomInfo.roomStatus === "OPEN" && revieweeData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-waiting">{MESSAGES.GUIDANCE.EMPTY_REVIEWEE}</p>
        <p className="process-waiting">{MESSAGES.GUIDANCE.SUB_DESCRIPTION}</p>
      </S.GuidanceWrapper>
    );
  }

  // 리뷰어일 때 매칭된 리뷰이 없으면 보여줄 화면
  if (roomInfo.memberRole === "REVIEWER" && revieweeData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-paused">{MESSAGES.GUIDANCE.REVIEWEE_FAIL_MATCHED}</p>
      </S.GuidanceWrapper>
    );
  }

  // 매칭 후 PR 제출 안 해서 매칭 실패했을 때 보여줄 화면
  if (roomInfo.participationStatus === "PULL_REQUEST_NOT_SUBMITTED") {
    return (
      <S.GuidanceWrapper>
        <p className="process-paused">{MESSAGES.GUIDANCE.PULL_REQUEST_NOT_SUBMITTED}</p>
      </S.GuidanceWrapper>
    );
  }

  // 방 종료 후 실패했을 때 보여줄 화면
  if (roomInfo.roomStatus === "CLOSE" && revieweeData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-paused">{MESSAGES.GUIDANCE.FAIL_MATCHED}</p>
      </S.GuidanceWrapper>
    );
  }

  // 매칭 후 성공했을 때 보여줄 화면
  return (
    <>
      <S.MyRevieweeTable aria-label="나의 리뷰이 목록.">
        <S.MyRevieweeTableHead>
          <S.MyRevieweeTableRow>
            <S.MyRevieweeTableHeader>아이디</S.MyRevieweeTableHeader>
            <S.MyRevieweeTableHeader>PR 링크</S.MyRevieweeTableHeader>
            <S.MyRevieweeTableHeader>리뷰 및 피드백 여부</S.MyRevieweeTableHeader>
          </S.MyRevieweeTableRow>
        </S.MyRevieweeTableHead>

        <S.MyRevieweeTableBody>
          {revieweeData?.map((reviewee) => (
            <S.MyRevieweeTableRow key={reviewee.userId}>
              <HoverStyledLink
                to={`/profile/${reviewee.username}`}
                aria-label={`${reviewee.username}. 클릭하면 프로필로 이동합니다.`}
              >
                <S.MyRevieweeId>{reviewee.username}</S.MyRevieweeId>
              </HoverStyledLink>

              <S.MyRevieweeContent>
                <HoverStyledLink
                  to={reviewee.link}
                  target="_blank"
                  rel="noreferrer"
                  aria-label={`바로가기. 클릭하면 리뷰이의 PR 링크로 이동합니다.`}
                >
                  <S.PRLink>
                    <S.IconWrapper>
                      <Icon kind="link" size="1.8rem" />
                    </S.IconWrapper>
                    바로가기
                  </S.PRLink>
                </HoverStyledLink>
              </S.MyRevieweeContent>

              <S.MyRevieweeContent>{renderRevieweeButton(reviewee)}</S.MyRevieweeContent>
            </S.MyRevieweeTableRow>
          ))}
        </S.MyRevieweeTableBody>
      </S.MyRevieweeTable>

      <S.ExtraInformation>※선호하는 리뷰이 인원수보다 적게 매칭될 수 있습니다.</S.ExtraInformation>
    </>
  );
};

export default MyReviewee;
