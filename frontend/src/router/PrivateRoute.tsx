import { Outlet } from "react-router-dom";
import Fallback from "@/components/common/errorBoundary/Fallback";
import { githubAuthUrl } from "@/config/githubAuthUrl";

const PrivateRoute = () => {
  const isLoggedIn = !!localStorage.getItem("accessToken");

  if (isLoggedIn) return <Outlet />;

  return (
    <Fallback
      message="로그인 후 이용 가능한 페이지입니다."
      buttonText="로그인하기"
      onButtonClick={() => (window.location.href = githubAuthUrl)}
    />
  );
};

export default PrivateRoute;
