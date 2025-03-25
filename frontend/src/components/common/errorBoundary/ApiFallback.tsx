import Fallback from "@/components/common/errorBoundary/Fallback";
import MESSAGES from "@/constants/message";

const ApiFallback = ({ onRetry }: { onRetry: () => void }) => {
  return (
    <Fallback
      message={MESSAGES.ERROR.BOUNDARY_API}
      buttonText="다시 시도하기"
      onButtonClick={onRetry}
    />
  );
};

export default ApiFallback;
