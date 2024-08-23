import { useCallback, useContext, useEffect, useRef } from "react";
import { ToastContext, ToastDispatchContext } from "@/providers/ToastProvider";

const useToast = (type: "error" | "success" = "error") => {
  const isOpenToast = useContext(ToastContext);
  const setIsOpenToast = useContext(ToastDispatchContext);
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  const openToast = useCallback((message: string) => {
    setIsOpenToast({ isOpen: true, message, type });
  }, []);

  useEffect(() => {
    if (isOpenToast.isOpen) {
      timerRef.current = setTimeout(() => {
        setIsOpenToast({ isOpen: false, message: "", type });
      }, 2500);
      return;
    }

    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
        timerRef.current = null;
      }
    };
  }, [isOpenToast]);

  return { openToast };
};

export default useToast;
