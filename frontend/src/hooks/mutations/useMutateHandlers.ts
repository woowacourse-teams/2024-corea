import useToast from "@/hooks/common/useToast";

const useMutateHandlers = () => {
  const { openToast } = useToast();

  const handleMutateError = (error: Error) => {
    openToast(error.message, "error");
  };

  return { handleMutateError };
};

export default useMutateHandlers;
