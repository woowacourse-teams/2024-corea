import React, { useEffect } from "react";
import useMutateLogin from "@/hooks/mutations/useMutateAuth";
import * as S from "@/pages/callback/CallbackPage.style";
import { thinkingCharacter } from "@/assets";

const CallbackPage = () => {
  const { postLoginMutation } = useMutateLogin();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");

    if (code) {
      postLoginMutation.mutate(code);
    }
  }, []);

  return (
    <S.CallbackPageContainer>
      <S.Character src={thinkingCharacter} alt="로그인 중" />
      <S.LoadingContainer>
        <S.LoadingBar />
        <p>로그인 중...</p>
      </S.LoadingContainer>
    </S.CallbackPageContainer>
  );
};

export default CallbackPage;
