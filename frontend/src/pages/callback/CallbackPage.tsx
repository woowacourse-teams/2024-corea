import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useMutateLogin from "@/hooks/mutations/useMutateAuth";

const CallbackPage = () => {
  const navigate = useNavigate();
  const params = new URLSearchParams(window.location.search);
  const code = params.get("code");
  const { postLoginMutation } = useMutateLogin();

  const getCode = () => {
    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");
    if (code) {
      postLoginMutation.mutate(code);
    }
  };

  useEffect(() => getCode(), []);

  if (postLoginMutation.isError) {
    setTimeout(() => {
      navigate("/");
    });
    return <div>로그인을 하던 도중 에러가 발생하였습니다. 메인페이지로 이동합니다.</div>;
  }

  return <div>로그인 중...</div>;
};

export default CallbackPage;
