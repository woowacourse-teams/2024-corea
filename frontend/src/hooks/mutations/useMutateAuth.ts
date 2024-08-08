import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { UserInfo } from "@/@types/userInfo";
import { postLogin, postLogout } from "@/apis/auth.api";

const useMutateAuth = () => {
  const navigate = useNavigate();
  const { handleMutateSuccess, handleMutateError } = useMutateHandlers();

  const postLoginMutation = useMutation({
    mutationFn: (code: string) => postLogin(code),
    onSuccess: ({
      accessToken,
      refreshToken,
      userInfo,
    }: {
      accessToken: string;
      refreshToken: string;
      userInfo: UserInfo;
    }) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
      navigate("/");
    },
    onError: (error) => {
      localStorage.clear();
      handleMutateError(error);
    },
    networkMode: "always",
  });

  const postLogoutMutation = useMutation({
    mutationFn: () => postLogout(),
    onSuccess: () => {
      localStorage.clear();
    },
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  return { postLoginMutation, postLogoutMutation };
};

export default useMutateAuth;
