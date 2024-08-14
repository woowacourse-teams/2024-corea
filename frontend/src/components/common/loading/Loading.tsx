import * as S from "./Loading.style";
import { spinner } from "@/assets";

const Loading = () => {
  return (
    <S.LoadingContainer>
      <img src={spinner} />
    </S.LoadingContainer>
  );
};

export default Loading;
