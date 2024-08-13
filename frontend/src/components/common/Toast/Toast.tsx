import * as S from "./Toast.style";
import { useContext, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import { ToastContext } from "@/providers/ToastProvider";

const Toast = () => {
  const toastContainer = document.getElementById("toast");
  const toastInfo = useContext(ToastContext);
  const [isOpen, setIsOpen] = useState<boolean>(false);

  useEffect(() => {
    if (toastInfo.isOpen) {
      setIsOpen(true);
      return;
    }

    const timer = setTimeout(() => {
      setIsOpen(false);
    }, 500);

    return () => clearTimeout(timer);
  }, [toastInfo.isOpen]);

  if (!toastContainer || !isOpen) {
    return <></>;
  }

  return createPortal(
    <S.Wrapper closeAnimation={!toastInfo.isOpen}>{toastInfo.message}</S.Wrapper>,
    toastContainer,
  );
};

export default Toast;
