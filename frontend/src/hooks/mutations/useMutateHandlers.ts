import useToast from "@/hooks/common/useToast";

const useMutateHandlers = () => {
  const { openToast } = useToast("error");

  const handleMutateError = (error: Error) => {
    openToast(error.message);
  };

  return { handleMutateError };
};

export default useMutateHandlers;
