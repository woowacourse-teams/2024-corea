import Fallback from "@/components/common/errorBoundary/Fallback";

const ApiFallback = ({ onRetry, errorMessage }: { onRetry: () => void; errorMessage: string }) => {
  return <Fallback message={errorMessage} buttonText="다시 시도하기" onButtonClick={onRetry} />;
};

export default ApiFallback;
