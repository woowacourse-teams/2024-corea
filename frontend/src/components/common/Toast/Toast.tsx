import * as S from "./Toast.style";
import React from "react";
import { useContext, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import { ToastContext } from "@/providers/ToastProvider";

const Toast = () => {
  const toastContainer = document.getElementById("toast");
  const modalInfo = useContext(ToastContext);
  const [isOpen, setIsOpen] = useState<boolean>(false);

  useEffect(() => {
    if (modalInfo.isOpen) {
      setIsOpen(true);
      return;
    }

    const timer = setTimeout(() => {
      setIsOpen(false);
    }, 500);

    return () => clearTimeout(timer);
  }, [modalInfo.isOpen]);

  if (!toastContainer || !isOpen) {
    return <></>;
  }

  return createPortal(
    <S.Wrapper closeAnimation={!modalInfo.isOpen}>{modalInfo.message}</S.Wrapper>,
    toastContainer,
  );
};

export default Toast;
