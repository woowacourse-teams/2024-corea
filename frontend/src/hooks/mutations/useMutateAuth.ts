import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { UserInfo } from "@/@types/userInfo";
import { postLogin, postLogout } from "@/apis/auth.api";
import QUERY_KEYS from "@/apis/queryKeys";

interface UserInfoResponse {
  accessToken: string;
  refreshToken: string;
  userInfo: UserInfo;
}
const useMutateAuth = () => {
  const navigate = useNavigate();
  const { handleMutateError } = useMutateHandlers();
  const queryClient = useQueryClient();

  const postLoginMutation = useMutation({
    mutationFn: (code: string) => postLogin(code),
    onSuccess: ({ accessToken, refreshToken, userInfo }: UserInfoResponse) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
    },
    onError: (error) => {
      localStorage.clear();
      handleMutateError(error);
    },
  });

  const postLogoutMutation = useMutation({
    mutationFn: () => postLogout(),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PROGRESS_ROOM_LIST] });
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.OPENED_ROOM_LIST] });
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST] });
      localStorage.clear();
      window.location.replace("/");
    },
    onError: (error) => handleMutateError(error),
  });

  return { postLoginMutation, postLogoutMutation };
};

export default useMutateAuth;
