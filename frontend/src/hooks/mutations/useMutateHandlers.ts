import { useQueryClient } from "@tanstack/react-query";
import useToast from "@/hooks/common/useToast";

const useMutateHandlers = () => {
  const queryClient = useQueryClient();
  const { openToast } = useToast("error");

  const handleMutateSuccess = (queryKeys: string[]) => {
    queryKeys.forEach((queryKey) => {
      queryClient.invalidateQueries({ queryKey: [queryKey] });
    });
  };

  const handleMutateError = (error: Error) => {
    openToast(error.message);
  };

  return { handleMutateSuccess, handleMutateError };
};

export default useMutateHandlers;
