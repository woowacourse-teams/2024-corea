import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { UserInfo } from "@/@types/userInfo";
import { postLogin, postLogout } from "@/apis/auth.api";
import QUERY_KEYS from "@/apis/queryKeys";

export interface UserInfoResponse {
  accessToken: string;
  refreshToken: string;
  userInfo: UserInfo;
  memberRole: string;
}

const useMutateAuth = () => {
  const { handleMutateError } = useMutateHandlers();
  const queryClient = useQueryClient();

  const postLoginMutation = useMutation({
    mutationFn: (code: string) => postLogin(code),
    onSuccess: ({ accessToken, refreshToken, userInfo, memberRole }: UserInfoResponse) => {
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("userInfo", JSON.stringify(userInfo));
      localStorage.setItem("memberRole", memberRole);
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
