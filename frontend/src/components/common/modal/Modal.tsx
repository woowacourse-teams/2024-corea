import React, { CSSProperties, MouseEvent, useEffect } from "react";
import { createPortal } from "react-dom";
import * as S from "@/components/common/modal/Modal.style";

const portalElement = document.getElementById("modal") as HTMLElement;

export interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  hasCloseButton?: boolean;
  style?: CSSProperties;
  children?: React.ReactNode;
}

const Modal = ({ isOpen, onClose, hasCloseButton = true, style, children }: ModalProps) => {
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isOpen]);

  if (!isOpen) return null;

  const handleModalContainerClick = (event: MouseEvent<HTMLDivElement>) => {
    event.stopPropagation();
  };

  return createPortal(
    <>
      <S.BackDrop onClick={onClose} />
      <S.ModalContent onClick={handleModalContainerClick} style={style}>
        {hasCloseButton && <S.CloseButton onClick={onClose}>&times;</S.CloseButton>}
        {children}
      </S.ModalContent>
    </>,
    portalElement,
  );
};

export default Modal;
