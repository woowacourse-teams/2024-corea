import { useContext } from "react";
import type { ToastType } from "@/@types/toast";
import { ToastDispatchContext } from "@/providers/ToastProvider";

/**
 * @example
 * const { openToast } = useToast();
 * openToast("삭제되었습니다", "success");
 */

const useToast = () => {
  const setToast = useContext(ToastDispatchContext);

  const openToast = (message: string, type: ToastType = "error") => {
    setToast({ isOpen: true, message, type });

    setTimeout(() => {
      setToast((prev) => ({ ...prev, isOpen: false, type }));
    }, 2500);
  };

  return { openToast };
};

export default useToast;
