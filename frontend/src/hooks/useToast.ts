import { useCallback, useContext, useEffect } from "react";
import { ToastContext, ToastDispatchContext } from "@/providers/ToastProvider";

const useToast = () => {
  const isOpenModal = useContext(ToastContext);
  const setIsOpenModal = useContext(ToastDispatchContext);

  const openToast = useCallback((message: string) => {
    setIsOpenModal({ isOpen: true, message });
  }, []);

  useEffect(() => {
    let timer: NodeJS.Timeout;
    if (isOpenModal.isOpen) {
      timer = setTimeout(() => {
        setIsOpenModal({ isOpen: false, message: "" });
      }, 2500);
    }

    return () => clearTimeout(timer);
  }, [isOpenModal]);

  return { openToast };
};

export default useToast;
