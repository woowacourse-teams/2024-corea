import * as S from "@/components/common/loading/Loading.style";
import { spinner } from "@/assets";

const Loading = () => {
  return (
    <S.LoadingContainer>
      <img src={spinner} alt="로딩 스피너" />
    </S.LoadingContainer>
  );
};

export default Loading;
