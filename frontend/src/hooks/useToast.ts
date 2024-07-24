import { useCallback, useContext, useEffect, useRef } from "react";
import { ToastContext, ToastDispatchContext } from "@/providers/ToastProvider";

const useToast = () => {
  const isOpenModal = useContext(ToastContext);
  const setIsOpenModal = useContext(ToastDispatchContext);
  const timerRef = useRef<NodeJS.Timeout | null>(null);

  const openToast = useCallback((message: string) => {
    setIsOpenModal({ isOpen: true, message });
  }, []);

  useEffect(() => {
    if (isOpenModal.isOpen) {
      timerRef.current = setTimeout(() => {
        setIsOpenModal({ isOpen: false, message: "" });
      }, 2500);
      return;
    }

    return () => {
      if (timerRef.current) {
        clearTimeout(timerRef.current);
        timerRef.current = null;
      }
    };
  }, [isOpenModal]);

  return { openToast };
};

export default useToast;
