import { useNavigate } from "react-router-dom";
import Fallback from "@/components/common/errorBoundary/Fallback";

const NotFoundPage = () => {
  const navigate = useNavigate();

  return (
    <Fallback
      message="해당 페이지를 찾을 수 없습니다."
      buttonText="홈 화면으로 가기"
      onButtonClick={() => navigate("/")}
    />
  );
};

export default NotFoundPage;
