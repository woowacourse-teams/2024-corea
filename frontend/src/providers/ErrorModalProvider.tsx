import { Dispatch, ReactNode, SetStateAction, createContext, useState } from "react";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";

export type ErrorModalState = {
  isOpen: boolean;
  message: string;
  onConfirm?: () => void;
  onCancel?: () => void;
  confirmButtonText?: string;
  cancelButtonText?: string;
};

export const ModalContext = createContext<ErrorModalState | null>(null);
export const ModalDispatchContext = createContext<Dispatch<SetStateAction<ErrorModalState>>>(
  () => {},
);

export const ErrorModalProvider = ({ children }: { children: ReactNode }) => {
  const [modalInfo, setModalInfo] = useState<ErrorModalState>({
    isOpen: false,
    message: "",
  });

  return (
    <ModalContext.Provider value={modalInfo}>
      <ModalDispatchContext.Provider value={setModalInfo}>
        {children}
        <ConfirmModal
          isOpen={modalInfo.isOpen}
          onClose={() => {
            setModalInfo((prev) => ({ ...prev, isOpen: false }));
            modalInfo.onCancel?.();
          }}
          onConfirm={() => {
            setModalInfo((prev) => ({ ...prev, isOpen: false }));
            modalInfo.onConfirm?.();
          }}
          onCancel={() => {
            setModalInfo((prev) => ({ ...prev, isOpen: false }));
            modalInfo.onCancel?.();
          }}
          confirmButtonText={modalInfo.confirmButtonText}
          cancelButtonText={modalInfo.cancelButtonText}
        >
          {modalInfo.message}
        </ConfirmModal>
      </ModalDispatchContext.Provider>
    </ModalContext.Provider>
  );
};
