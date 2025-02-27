import Icon from "../icon/Icon";
import { CSSProperties, MouseEvent, ReactNode, useEffect, useRef, useState } from "react";
import { createPortal } from "react-dom";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import * as S from "@/components/common/modal/Modal.style";

const portalElement = document.getElementById("modal") as HTMLElement;

export interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  hasCloseButton?: boolean;
  style?: CSSProperties;
  children?: ReactNode;
}

const Modal = ({ isOpen, onClose, hasCloseButton = true, style, children }: ModalProps) => {
  const [isClosing, setIsClosing] = useState(false);
  const previousFocusedElement = useRef<HTMLElement | null>(null);
  const firstChildRef = useRef<HTMLDivElement | null>(null);
  const closeButtonRef = useRef<HTMLButtonElement | null>(null);

  useEffect(() => {
    if (isOpen) {
      previousFocusedElement.current = document.activeElement as HTMLElement;
      document.documentElement.style.scrollbarGutter = "stable";
      document.body.style.overflow = "hidden";

      if (hasCloseButton && closeButtonRef.current) {
        closeButtonRef.current.focus();
      } else if (firstChildRef.current) {
        firstChildRef.current.focus();
      }
    } else {
      document.body.style.overflow = "auto";
      document.documentElement.style.scrollbarGutter = "auto";
    }

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

    return () => {
      previousFocusedElement.current?.focus();
      document.getElementById("root")?.setAttribute("aria-hidden", "false");
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
          <>
            <div ref={firstChildRef} tabIndex={-1} aria-hidden />
            {hasCloseButton && (
              <S.CloseButton onClick={handleModalClose} ref={closeButtonRef} aria-label="모달 닫기">
                <Icon kind="close" size="2.4rem" />
              </S.CloseButton>
            )}
            <S.ModalBody>{children}</S.ModalBody>
          </>
        </FocusTrap>
      </S.ModalContent>
    </>,
    portalElement,
  );
};

export default Modal;
