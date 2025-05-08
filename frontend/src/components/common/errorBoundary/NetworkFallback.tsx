import Fallback from "./Fallback";
import React from "react";
import MESSAGES from "@/constants/message";

const NetworkFallback = ({ onRetry }: { onRetry: () => void }) => {
  const handleRetry = () => {
    onRetry();
    window.location.reload();
  };
  return (
    <Fallback
      message={MESSAGES.ERROR.BOUNDARY_NETWORK}
      buttonText="새로고침 하기"
      onButtonClick={handleRetry}
    />
  );
};

export default NetworkFallback;
