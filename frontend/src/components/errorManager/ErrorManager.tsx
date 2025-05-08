/**
 * Error Handling 전략 설명
 *
 * Query (Suspense 기반)
 * - useSuspenseQuery 사용 시, 에러는 자동으로 ErrorBoundary로 전달됨
 * - fallback UI는 ErrorBoundary가 처리하지만, 전략에 따라 사용자에게 추가 알림 가능
 *
 * Mutation
 * - 기본 에러 전략은 TOAST
 * - 전략이 ERROR_BOUNDARY 또는 IGNORE인 경우, 화면이나 토스트에 아무 처리도 일어나지 않음
 *
 * ※ 모든 CustomError는 ErrorManager를 통해 처리되며, strategy 기반으로 UI 반영됨.
 */
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

    switch (error.strategy) {
      case ERROR_STRATEGY.TOAST:
        showToast(error.message);
        break;

      case ERROR_STRATEGY.MODAL: {
        const { onConfirm, onCancel, confirmButtonText, cancelButtonText } = error.meta ?? {};

        openErrorModal({
          message: error.message,
          onConfirm,
          onCancel,
          confirmButtonText,
          cancelButtonText,
        });
        break;
      }

      case ERROR_STRATEGY.REDIRECT: {
        const redirectTo = error.meta?.redirectTo ?? "/";
        window.location.href = redirectTo;
        break;
      }

      default:
        throw error;
    }

    setError(null);
  }, [error]);

  return children;
};

export default ErrorManager;
