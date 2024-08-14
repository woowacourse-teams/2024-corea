import { useNavigate } from "react-router-dom";
import Button from "@/components/common/button/Button";

const LogoutPage = () => {
  const navigate = useNavigate();
  return (
    <>
      <div>로그아웃 되었습니다.</div>
      <Button onClick={() => navigate("/")} size="medium">
        메인 페이지로 돌아갈까요?
      </Button>
    </>
  );
};

export default LogoutPage;
