import { useContext } from "react";
import { ErrorHandlingContext } from "@/providers/ErrorHandlingProvider";

const useErrorHandling = () => {
  const context = useContext(ErrorHandlingContext);
  if (!context) throw new Error("ErrorHandlingProvider 내부에서만 해당 훅을 사용할 수 있습니다.");
  return context;
};

export default useErrorHandling;
