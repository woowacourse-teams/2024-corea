import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { UserInfo } from "@/@types/userInfo";
import { postLogin, postLogout } from "@/apis/auth.api";

interface UserInfoResponse {
  accessToken: string;
  refreshToken: string;
  userInfo: UserInfo;
}
const useMutateAuth = () => {
  const navigate = useNavigate();
  const { handleMutateError } = useMutateHandlers();

  const postLoginMutation = useMutation({
    mutationFn: (code: string) => postLogin(code),
    onSuccess: ({ accessToken, refreshToken, userInfo }: UserInfoResponse) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
    },
    onError: (error) => {
      console.log(error);
      localStorage.clear();
      handleMutateError(error);
    },
    networkMode: "always",
  });

  const postLogoutMutation = useMutation({
    mutationFn: () => postLogout(),
    onSuccess: () => {
      localStorage.clear();
      navigate("/");
    },
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  return { postLoginMutation, postLogoutMutation };
};

export default useMutateAuth;
