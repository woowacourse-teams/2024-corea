import { useContext, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import * as S from "@/components/common/Toast/Toast.style";
import { ToastContext } from "@/providers/ToastProvider";

const Toast = () => {
  const container = document.getElementById("toast");
  const { isOpen, message, type } = useContext(ToastContext);

  const [shouldRender, setShouldRender] = useState(false);

  useEffect(() => {
    if (isOpen) {
      setShouldRender(true);
      return;
    }

    const timeout = setTimeout(() => setShouldRender(false), 400);
    return () => clearTimeout(timeout);
  }, [isOpen]);

  if (!container || !shouldRender) return null;

  return createPortal(
    <S.Wrapper $type={type} $closeAnimation={!isOpen} role="alert" aria-live="assertive">
      {message}
    </S.Wrapper>,
    container,
  );
};

export default Toast;
