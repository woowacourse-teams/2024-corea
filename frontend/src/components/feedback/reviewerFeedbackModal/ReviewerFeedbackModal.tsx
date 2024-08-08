import React from "react";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import OptionButton from "@/components/feedback/keywordOptionButton/KeywordOptionButton";
import * as S from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal.style";
import { RoomInfo } from "@/@types/roomInfo";

interface ReviewerFeedbackModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: Pick<RoomInfo, "title" | "keywords">;
  buttonType: "create" | "edit" | "view";
}

const ReviewerFeedbackModal = ({
  isOpen,
  onClose,
  roomInfo,
  buttonType,
}: ReviewerFeedbackModalProps) => {
  const handleClick = () => {
    alert("피드백이 작성되었습니다.");
  };

  const getButtonText = (buttonType: "create" | "edit" | "view"): string => {
    switch (buttonType) {
      case "create":
        return "피드백 작성";
      case "edit":
        return "피드백 수정";
      case "view":
        return "피드백 확인";
    }
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <S.FeedbackContainer>
        <S.ModalType>리뷰어 피드백 작성하기</S.ModalType>
        <S.ModalTitle>{roomInfo.title}</S.ModalTitle>
        <S.Keywords>
          {roomInfo.keywords.map((keyword) => (
            <Label key={keyword} type="keyword" text={keyword} size="semiSmall" />
          ))}
        </S.Keywords>

        <S.ItemContainer>
          <S.ModalQuestion required>
            리뷰어의 소프트 스킬 역량 향상을 위해 피드백을 해주세요.
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
            placeholder="상대 리뷰어의 소프트 스킬 역량 향상을 위해 피드백을 남겨주세요."
          />
        </S.ItemContainer>

        <S.ButtonWrapper>
          <Button onClick={handleClick}>{getButtonText(buttonType)}</Button>
        </S.ButtonWrapper>
      </S.FeedbackContainer>
    </Modal>
  );
};

export default ReviewerFeedbackModal;
