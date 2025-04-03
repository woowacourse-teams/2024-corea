import React, { useEffect } from "react";
import useErrorModal from "@/hooks/common/useErrorModal";
import useErrorStore from "@/hooks/common/useErrorStore";
import useToast from "@/hooks/common/useToast";
import { ERROR_STRATEGY } from "@/constants/errorStrategy";
import { CustomError } from "@/utils/CustomError";

const ErrorManager = ({ children }: { children: React.ReactNode }) => {
  const { error, setError } = useErrorStore();
  const { showToast } = useToast();
  const { openErrorModal } = useErrorModal();

  useEffect(() => {
    if (!error) return;

    if (!(error instanceof CustomError)) {
      throw error;
    }

    if (error instanceof CustomError) {
      switch (error.strategy) {
        case ERROR_STRATEGY.TOAST:
          showToast(error.message);
          setError(null);
          break;

        case ERROR_STRATEGY.MODAL: {
          const { onConfirm, onCancel, confirmButtonText, cancelButtonText } = error.meta || {};

          openErrorModal({
            message: error.message,
            onConfirm: onConfirm as () => void,
            onCancel: onCancel as () => void,
            confirmButtonText: confirmButtonText as string,
            cancelButtonText: cancelButtonText as string,
          });
          setError(null);
          break;
        }

        case ERROR_STRATEGY.REDIRECT: {
          const redirectTo = error.meta?.redirectTo as string;
          window.location.href = redirectTo || "/";
          setError(null);
          break;
        }
      }
    } else {
      throw error;
    }

    setError(null);
  }, [error]);

  return children;
};

export default ErrorManager;
