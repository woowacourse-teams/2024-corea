import React from "react";
import Button from "@/components/common/button/Button";

export type FeedbackButtonType = "create" | "edit" | "view";

interface FeedbackButtonProps {
  type: FeedbackButtonType;
}

const FeedbackButton = ({ type }: FeedbackButtonProps) => {
  const handleClick = () => {
    alert("피드백이 작성되었습니다.");
  };

  const getButtonText = (type: FeedbackButtonType): string => {
    switch (type) {
      case "create":
        return "피드백 작성";
      case "edit":
        return "피드백 수정";
      case "view":
        return "피드백 확인";
    }
  };

  const getVariant = (type: FeedbackButtonType): "primary" | "disable" => {
    return type === "view" ? "disable" : "primary";
  };

  return (
    <Button variant={getVariant(type)} onClick={handleClick}>
      {getButtonText(type)}
    </Button>
  );
};

export default FeedbackButton;
