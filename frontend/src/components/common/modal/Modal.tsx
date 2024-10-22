import { CSSProperties, MouseEvent, ReactNode, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import * as S from "@/components/common/modal/Modal.style";

const portalElement = document.getElementById("modal") as HTMLElement;

export interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm?: () => void;
  onCancel?: () => void;
  hasCloseButton?: boolean;
  style?: CSSProperties;
  children?: ReactNode;
}

const Modal = ({ isOpen, onClose, hasCloseButton = true, style, children }: ModalProps) => {
  const [isClosing, setIsClosing] = useState(false);
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

  const handleModalClose = () => {
    setIsClosing(true);
    setTimeout(() => {
      onClose();
      setIsClosing(false);
    }, 500);
  };

  return createPortal(
    <>
      <S.BackDrop onClick={handleModalClose} />
      <S.ModalContent
        $isVisible={isOpen}
        $isClosing={isClosing}
        onClick={handleModalContainerClick}
        style={style}
      >
        <FocusTrap
          onEscapeFocusTrap={() => {
            handleModalClose();
          }}
        >
          <div>
            {children}
            {hasCloseButton && <S.CloseButton onClick={handleModalClose}>&times;</S.CloseButton>}
          </div>
        </FocusTrap>
      </S.ModalContent>
    </>,
    portalElement,
  );
};

export default Modal;
