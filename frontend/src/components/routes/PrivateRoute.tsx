import { Outlet } from "react-router-dom";
import * as S from "@/components/routes/PrivateRoute.style";
import { errorCharacter } from "@/assets";

const PrivateRoute = () => {
  const isLoggedIn = !!localStorage.getItem("accessToken");

  if (isLoggedIn) {
    return <Outlet />;
  }

  return (
    <S.PrivateRouteContainer>
      <img src={errorCharacter} alt="로그인 후 이용해주세요" />
      <p>로그인 후 이용 가능한 페이지입니다.</p>
    </S.PrivateRouteContainer>
  );
};

export default PrivateRoute;
