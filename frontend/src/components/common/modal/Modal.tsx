import React, { MouseEvent, useEffect } from "react";
import { createPortal } from "react-dom";
import * as S from "@/components/common/modal/Modal.style";

const portalElement = document.getElementById("modal") as HTMLElement;

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  children?: React.ReactNode;
}

const Modal = ({ isOpen, onClose, children }: ModalProps) => {
  useEffect(() => {
    document.body.style.overflow = isOpen ? "hidden" : "auto";
  }, [isOpen]);

  if (!isOpen) return null;

  const handleModalContainerClick = (event: MouseEvent<HTMLDivElement>) => {
    event.stopPropagation();
  };

  return createPortal(
    <>
      <S.BackDrop onClick={onClose} />
      <S.ModalContent onClick={handleModalContainerClick}>{children}</S.ModalContent>
    </>,
    portalElement,
  );
};

export default Modal;
