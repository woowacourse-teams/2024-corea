import { useContext } from "react";
import type { ToastType } from "@/@types/toast";
import { ToastContext, ToastDispatchContext } from "@/providers/ToastProvider";

const useToast = () => {
  const toasts = useContext(ToastContext);
  const setToasts = useContext(ToastDispatchContext);

  const showToast = (message: string, type: ToastType = "error", durationMs = 2500) => {
    setToasts((prev) => {
      const alreadyExists = prev.some((t) => t.message === message);
      if (alreadyExists) return prev;

      return [...prev, { message, type, durationMs }];
    });
    console.log(toasts);
  };

  const closeToast = (message: string) => {
    setToasts((prev) => prev.filter((t) => t.message !== message));
  };

  return { showToast, closeToast };
};

export default useToast;
