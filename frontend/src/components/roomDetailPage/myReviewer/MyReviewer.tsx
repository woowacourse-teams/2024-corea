import { useState } from "react";
import useModal from "@/hooks/common/useModal";
import { useFetchReviewer } from "@/hooks/queries/useFetchReviewer";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import ReviewerFeedbackModal from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewer/MyReviewer.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { thinkingCharacter } from "@/assets";
import MESSAGES from "@/constants/message";
import { HoverStyledLink } from "@/styles/common";
import { FeedbackTypeResult, getFeedbackType } from "@/utils/feedbackUtils";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewer = ({ roomInfo }: MyReviewerProps) => {
  const { data: reviewerData } = useFetchReviewer(roomInfo);
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();
  const [selectedReviewer, setSelectedReviewer] = useState<ReviewerInfo | null>(null);
  const [feedbackTypeResult, setFeedbackTypeResult] = useState<FeedbackTypeResult | null>(null);

  // 피드백 모달 여는 함수
  const handleOpenFeedbackModal = (reviewer: ReviewerInfo) => {
    const feedbackType = getFeedbackType({
      isReviewed: reviewer.isReviewed,
      isWrited: reviewer.isWrited,
      isClosed: roomInfo.roomStatus === "CLOSE",
    });
    setSelectedReviewer(reviewer);
    setFeedbackTypeResult(feedbackType);
    handleOpenModal();
  };

  // 피드백 여부 버튼 렌더링 함수
  const renderReviewerButton = (reviewer: ReviewerInfo) => {
    const { buttonText } = getFeedbackType({
      isReviewed: reviewer.isReviewed,
      isWrited: reviewer.isWrited,
      isClosed: roomInfo.roomStatus === "CLOSE",
    });

    if (roomInfo.roomStatus === "CLOSE" && !reviewer.isReviewed) {
      return <p>리뷰어가 리뷰를 안 했어요</p>;
    }

    if (roomInfo.roomStatus === "CLOSE" && !reviewer.isWrited) {
      return <p>피드백을 작성하지 않았어요</p>;
    }

    return reviewer.isReviewed ? (
      <Button
        size="xSmall"
        onClick={() => handleOpenFeedbackModal(reviewer)}
        variant={reviewer.isReviewed ? "secondary" : "disable"}
        disabled={!reviewer.isReviewed}
      >
        {buttonText}
      </Button>
    ) : (
      <p>리뷰어가 리뷰 중이에요!</p>
    );
  };

  // 리뷰어로만 참여하는 사람에게 보여줄 화면
  if (roomInfo.memberRole === "REVIEWER") {
    return (
      <S.GuidanceWrapper>
        <S.Character src={thinkingCharacter} alt="생각 중인 캐릭터" />
        <p className="process-paused">{MESSAGES.GUIDANCE.ONLY_REVIEWER}</p>
      </S.GuidanceWrapper>
    );
  }

  // 매칭 전 보여줄 화면
  if (roomInfo.roomStatus === "OPEN" && reviewerData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-waiting">{MESSAGES.GUIDANCE.EMPTY_REVIEWER}</p>
        <p className="process-waiting">{MESSAGES.GUIDANCE.SUB_DESCRIPTION}</p>
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
  if (roomInfo.roomStatus === "CLOSE" && reviewerData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-paused">{MESSAGES.GUIDANCE.FAIL_MATCHED}</p>
      </S.GuidanceWrapper>
    );
  }

  // 매칭 후 성공했을 때 보여줄 화면
  return (
    <>
      {selectedReviewer && feedbackTypeResult && (
        <ReviewerFeedbackModal
          key={selectedReviewer.username}
          isOpen={isModalOpen}
          onClose={() => {
            handleCloseModal();
            setSelectedReviewer(null);
            setFeedbackTypeResult(null);
          }}
          roomInfo={roomInfo}
          reviewer={selectedReviewer}
          modalType={feedbackTypeResult.modalType}
          buttonText={feedbackTypeResult.buttonText}
        />
      )}

      <S.MyReviewerTable aria-label="나의 리뷰어 목록.">
        <S.MyReviewerTableHead>
          <S.MyReviewerTableRow>
            <S.MyReviewerTableHeader>아이디</S.MyReviewerTableHeader>
            <S.MyReviewerTableHeader>Comment 링크</S.MyReviewerTableHeader>
            <S.MyReviewerTableHeader>피드백 여부</S.MyReviewerTableHeader>
          </S.MyReviewerTableRow>
        </S.MyReviewerTableHead>

        <S.MyReviewerTableBody>
          {reviewerData.map((reviewer) => {
            return (
              <S.MyReviewerTableRow key={reviewer.userId}>
                <HoverStyledLink
                  to={`/profile/${reviewer.username}`}
                  aria-label={`${reviewer.username}. 클릭하면 프로필로 이동합니다.`}
                >
                  <S.MyReviewerId>{reviewer.username}</S.MyReviewerId>
                </HoverStyledLink>

                {reviewer.link.length !== 0 ? (
                  <S.MyReviewerContent>
                    <HoverStyledLink
                      to={reviewer.link}
                      target="_blank"
                      rel="noreferrer"
                      aria-label={`바로가기. 클릭하면 리뷰어의 코멘트 링크로 이동합니다.`}
                    >
                      <S.PRLink>
                        <S.IconWrapper>
                          <Icon kind="link" size="1.8rem" />
                        </S.IconWrapper>
                        바로가기
                      </S.PRLink>
                    </HoverStyledLink>
                  </S.MyReviewerContent>
                ) : (
                  "-"
                )}

                <S.MyReviewerContent>{renderReviewerButton(reviewer)}</S.MyReviewerContent>
              </S.MyReviewerTableRow>
            );
          })}
        </S.MyReviewerTableBody>
      </S.MyReviewerTable>

      <S.ExtraInformation>※최소 상호 리뷰 인원수보다 많게 매칭될 수 있습니다.</S.ExtraInformation>
    </>
  );
};

export default MyReviewer;
