import Button from "../common/button/Button";
import { Outlet, useNavigate } from "react-router-dom";
import * as S from "@/components/routes/PrivateRoute.style";
import { errorCharacter } from "@/assets";

const PrivateRoute = () => {
  const navigate = useNavigate();
  const isLoggedIn = !!localStorage.getItem("accessToken");

  if (isLoggedIn) {
    return <Outlet />;
  }

  return (
    <S.PrivateRouteContainer>
      <img src={errorCharacter} alt="로그인 후 이용해주세요" />
      <p>로그인 후 이용 가능한 페이지입니다.</p>
      <Button onClick={() => navigate("/")}>홈 화면으로 가기</Button>
    </S.PrivateRouteContainer>
  );
};

export default PrivateRoute;
