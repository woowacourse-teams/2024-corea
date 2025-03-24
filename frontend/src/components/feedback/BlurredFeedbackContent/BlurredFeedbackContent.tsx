import * as S from "./BlurredFeedbackContent.style";
import React from "react";
import Button from "@/components/common/button/Button";

interface BlurredFeedbackContentProps {
  feedbackType: "develop" | "social";
  onClick: () => void;
}

const BlurredFeedbackContent = ({ feedbackType, onClick }: BlurredFeedbackContentProps) => {
  return (
    <S.Overlay>
      <S.ButtonWrapper>
        <p>상대방 피드백을 작성해야 볼 수 있습니다.</p>
        <Button variant={feedbackType === "develop" ? "primary" : "secondary"} onClick={onClick}>
          피드백 작성하러가기
        </Button>
      </S.ButtonWrapper>
    </S.Overlay>
  );
};

export default BlurredFeedbackContent;
