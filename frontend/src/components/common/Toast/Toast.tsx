import { useContext, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import useToast from "@/hooks/common/useToast";
import * as S from "@/components/common/Toast/Toast.style";
import { ToastContext } from "@/providers/ToastProvider";

const Toast = () => {
  const container = document.getElementById("toast");
  const toasts = useContext(ToastContext);
  const { closeToast } = useToast();

  const [closingToasts, setClosingToasts] = useState<string[]>([]);

  /**
   * toasts 중에서 durationMs가 지난 toast는 closingToasts에 담습니다.
   */
  useEffect(() => {
    const timers = toasts.map((toast) => {
      if (!closingToasts.includes(toast.message) && Number.isFinite(toast.durationMs)) {
        return setTimeout(() => {
          setClosingToasts((prev) => [...prev, toast.message]);
        }, toast.durationMs);
      }
    });

    return () => {
      timers.forEach(clearTimeout);
    };
  }, [toasts, closingToasts]);

  /**
   * closingToasts 중 완벽히 화면에서 사라진 toast는 제거합니다.
   */
  useEffect(() => {
    const timers = closingToasts.map((message) =>
      setTimeout(() => {
        closeToast(message);
        setClosingToasts((prev) => prev.filter((m) => m !== message));
      }, 400),
    );

    return () => {
      timers.forEach(clearTimeout);
    };
  }, [closingToasts, closeToast]);

  if (!container) return null;

  return createPortal(
    <S.ToastContainer>
      {toasts.map(({ message, type }) => (
        <S.Wrapper
          key={message}
          $type={type}
          $closeAnimation={closingToasts.includes(message)}
          role="alert"
          aria-live="assertive"
        >
          {message}
        </S.Wrapper>
      ))}
    </S.ToastContainer>,
    container,
  );
};

export default Toast;
