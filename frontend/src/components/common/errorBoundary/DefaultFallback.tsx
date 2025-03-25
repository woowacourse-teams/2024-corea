import Fallback from "./Fallback";
import MESSAGES from "@/constants/message";

const DefaultFallback = ({ onRetry }: { onRetry: () => void }) => {
  return (
    <Fallback
      message={MESSAGES.ERROR.BOUNDARY_TOTAL}
      buttonText="다시 시도하기"
      onButtonClick={onRetry}
    />
  );
};

export default DefaultFallback;
