import useToast from "@/hooks/common/useToast";

const useMutateHandlers = () => {
  const { showToast } = useToast();

  const handleMutateError = (error: Error) => {
    showToast(error.message, "error");
  };

  return { handleMutateError };
};

export default useMutateHandlers;
