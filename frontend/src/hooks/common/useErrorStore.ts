import { useContext } from "react";
import { ErrorContext, ErrorDispatchContext } from "@/providers/ErrorProvider";

const useErrorStore = () => {
  const error = useContext(ErrorContext);
  const setError = useContext(ErrorDispatchContext);

  if (error === undefined || setError === undefined) {
    throw new Error("ErrorProvider 내부에서만 해당 훅을 사용할 수 있어요");
  }

  return { error, setError };
};

export default useErrorStore;
