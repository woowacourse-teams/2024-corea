import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import useToast from "@/hooks/common/useToast";
import useMutateLogin from "@/hooks/mutations/useMutateAuth";
import MESSAGES from "@/constants/message";

const CallbackPage = () => {
  const navigate = useNavigate();
  const { openToast } = useToast();
  const { postLoginMutation } = useMutateLogin();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");
    if (code) {
      postLoginMutation.mutate(code);
    }
  }, []);

  if (postLoginMutation.isError) {
    navigate("/");
    openToast(MESSAGES.ERROR.POST_LOGIN);
  }

  return <div>로그인 중...</div>;
};

export default CallbackPage;
