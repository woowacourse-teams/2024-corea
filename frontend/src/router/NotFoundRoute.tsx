import * as S from "./PrivateRoute.style";
import { useNavigate } from "react-router-dom";
import Button from "@/components/common/button/Button";
import { errorCharacter } from "@/assets";

const NotFoundRoute = () => {
  const navigate = useNavigate();

  return (
    <S.PrivateRouteContainer>
      <img src={errorCharacter} alt="로그인 후 이용해주세요" />
      <p>해당 페이지를 찾을 수 없습니다.</p>
      <Button onClick={() => navigate("/")}>홈 화면으로 가기</Button>
    </S.PrivateRouteContainer>
  );
};

export default NotFoundRoute;
