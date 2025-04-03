import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { UserInfo } from "@/@types/userInfo";
import { postLogin, postLogout } from "@/apis/auth.api";

export interface UserInfoResponse {
  accessToken: string;
  userInfo: UserInfo;
  memberRole: string;
}

const useMutateAuth = () => {
  const navigate = useNavigate();

  const postLoginMutation = useMutation({
    mutationFn: (code: string) => postLogin(code),
    onSuccess: ({ accessToken, userInfo, memberRole }: UserInfoResponse) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
      localStorage.setItem("memberRole", memberRole);
      navigate("/", { replace: true });
    },
    onError: () => {
      localStorage.clear();
    },
  });

  const postLogoutMutation = useMutation({
    mutationFn: () => postLogout(),
    onSuccess: () => {
      localStorage.clear();
      window.location.replace("/");
    },
  });

  return { postLoginMutation, postLogoutMutation };
};

export default useMutateAuth;
