import React from "react";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import FeedbackButton from "@/components/feedback/feedbackButton/FeedbackButton";
import OptionButton from "@/components/feedback/optionButton/OptionButton";
import RecommendationPointBar from "@/components/feedback/recommendationPointBar/RecommendationPointBar";
import * as S from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal.style";
import { RoomInfo } from "@/@types/roomInfo";

interface RevieweeFeedbackModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: Pick<RoomInfo, "title" | "keywords">;
}

const RevieweeFeedbackModal = ({ isOpen, onClose, roomInfo }: RevieweeFeedbackModalProps) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <S.FeedbackContainer>
        <S.ModalType>리뷰이 피드백 작성하기</S.ModalType>
        <S.ModalTitle>{roomInfo.title}</S.ModalTitle>
        <S.Keywords>
          {roomInfo.keywords.map((keyword) => (
            <Label key={keyword} type="keyword" text={keyword} size="semiSmall" />
          ))}
        </S.Keywords>

        <S.ItemContainer>
          <S.ModalQuestion required>
            리뷰이의 개발 역량 향상을 위해 코드를 평가 해주세요.
          </S.ModalQuestion>
          <EvaluationPointBar />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion required>어떤 점이 만족스러웠나요?</S.ModalQuestion>
          <OptionButton />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion>추가적으로 하고 싶은 피드백이 있다면 남겨 주세요.</S.ModalQuestion>
          <Textarea
            rows={5}
            maxLength={512}
            placeholder="상대 리뷰이의 개발 역량 향상을 위해 피드백을 남겨주세요."
          />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion required>리뷰이의 코드를 추천하시나요?</S.ModalQuestion>
          <RecommendationPointBar />
        </S.ItemContainer>

        <S.ButtonWrapper>
          <FeedbackButton type="create" />
        </S.ButtonWrapper>
      </S.FeedbackContainer>
    </Modal>
  );
};

export default RevieweeFeedbackModal;
