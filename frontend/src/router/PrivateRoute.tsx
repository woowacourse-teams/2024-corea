import Button from "../components/common/button/Button";
import { Outlet } from "react-router-dom";
import * as S from "@/components/common/errorBoundary/Fallback.style";
import { errorCharacterBase64 } from "@/assets/character/error-charactor-base64";
import { githubAuthUrl } from "@/config/githubAuthUrl";

const PrivateRoute = () => {
  const isLoggedIn = !!localStorage.getItem("accessToken");

  if (isLoggedIn) {
    return <Outlet />;
  }

  return (
    <S.FallbackContainer>
      <S.Character
        src={navigator.onLine ? "/error-character.png" : errorCharacterBase64}
        alt="로그인 후 이용해주세요"
      />
      <S.ErrorMessage>로그인 후 이용 가능한 페이지입니다.</S.ErrorMessage>
      <Button onClick={() => (window.location.href = githubAuthUrl)} size="medium">
        로그인하기
      </Button>
    </S.FallbackContainer>
  );
};

export default PrivateRoute;
