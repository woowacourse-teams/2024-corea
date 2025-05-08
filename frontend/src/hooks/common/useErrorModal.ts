import { useContext } from "react";
import {
  type ErrorModalState,
  ModalContext,
  ModalDispatchContext,
} from "@/providers/ErrorModalProvider";

const useErrorModal = () => {
  const modalInfo = useContext(ModalContext);
  const setModalInfo = useContext(ModalDispatchContext);

  const openErrorModal = (props: Omit<ErrorModalState, "isOpen">) => {
    setModalInfo({ isOpen: true, ...props });
  };

  const closeErrorModal = () => {
    setModalInfo((prev) => ({ ...prev, isOpen: false }));
  };

  return { ...modalInfo, openErrorModal, closeErrorModal };
};

export default useErrorModal;
