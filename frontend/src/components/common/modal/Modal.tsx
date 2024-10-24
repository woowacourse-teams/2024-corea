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
    [...document.body.children].forEach((element) => {
      if (element.id === "toast") return;

      if (element.id === "modal") {
        element.removeAttribute("aria-hidden");
        return;
      }

      if (isOpen) {
        element.setAttribute("aria-hidden", "true");
        return;
      }

      element.removeAttribute("aria-hidden");
    });

    if (isOpen) {
      document.body.style.overflow = "hidden";
      document.documentElement.style.scrollbarGutter = "stable";
    } else {
      document.body.style.overflow = "auto";
      document.documentElement.style.scrollbarGutter = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
      document.documentElement.style.scrollbarGutter = "auto";
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
            {hasCloseButton && (
              <S.CloseButton onClick={handleModalClose} aria-label="모달 닫기">
                &times;
              </S.CloseButton>
            )}
            {children}
          </div>
        </FocusTrap>
      </S.ModalContent>
    </>,
    portalElement,
  );
};

export default Modal;
