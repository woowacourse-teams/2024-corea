import React, { createContext } from "react";
import useErrorModal from "@/hooks/common/useErrorModal";
import useToast from "@/hooks/common/useToast";
import { ERROR_STRATEGY } from "@/constants/errorStrategy";
import { CustomError } from "@/utils/Errors";

interface ErrorHandlingContextType {
  notifyError: (error: unknown) => void;
}

export const ErrorHandlingContext = createContext<ErrorHandlingContextType | null>(null);

export const ErrorHandlingProvider = ({ children }: { children: React.ReactNode }) => {
  const { showToast } = useToast();
  const { openErrorModal } = useErrorModal();

  const notifyError = (error: unknown) => {
    if (!(error instanceof Error)) return;

    if (!(error instanceof CustomError)) {
      console.error("예상치 못한 에러:", error);
      return;
    }

    const strategy = error instanceof CustomError ? error.strategy : ERROR_STRATEGY.TOAST;

    switch (strategy) {
      case ERROR_STRATEGY.TOAST:
        showToast(error.message);
        break;
      case ERROR_STRATEGY.MODAL:
        openErrorModal({
          message: error.message,
          onConfirm: error.onConfirm,
          onCancel: error.onCancel,
          confirmButtonText: error.confirmButtonText,
          cancelButtonText: error.cancelButtonText,
        });
        break;
      case ERROR_STRATEGY.REDIRECT:
        window.location.href = "/";
        break;
      case ERROR_STRATEGY.ERROR_BOUNDARY:
        throw error;
      case ERROR_STRATEGY.IGNORE:
        break;
      default:
        console.error("이 에러는 strategy가 없습니다.", error);
        break;
    }
  };

  return (
    <ErrorHandlingContext.Provider value={{ notifyError }}>
      {children}
    </ErrorHandlingContext.Provider>
  );
};
