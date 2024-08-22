import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useMutateLogin from "@/hooks/mutations/useMutateAuth";
import * as S from "@/pages/callback/CallbackPage.style";
import { thingkingCharacter } from "@/assets";

const CallbackPage = () => {
  const navigate = useNavigate();
  const { postLoginMutation } = useMutateLogin();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");

    if (code) {
      postLoginMutation.mutate(code);
    }

    const timer = setTimeout(() => {
      navigate("/");
    }, 2000);

    return () => clearTimeout(timer);
  }, []);

  return (
    <S.CallbackPageContainer>
      <S.Character src={thingkingCharacter} alt="로그인 중" />
      <S.LoadingContainer>
        로그인 중 ...
        <S.LoadingBar />
      </S.LoadingContainer>
    </S.CallbackPageContainer>
  );
};

export default CallbackPage;
