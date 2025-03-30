import { type ReactNode, createContext, useState } from "react";
import type { Toast } from "@/@types/toast";

export const ToastContext = createContext<Toast[]>([]);

export const ToastDispatchContext = createContext<React.Dispatch<React.SetStateAction<Toast[]>>>(
  () => {},
);

/**
 * @example
 * const { showToast } = useToast();
 * showToast("삭제되었습니다", "success", Infinity)
 */
export const ToastProvider = ({ children }: { children: ReactNode }) => {
  const [toasts, setToasts] = useState<Toast[]>([]);

  return (
    <ToastContext.Provider value={toasts}>
      <ToastDispatchContext.Provider value={setToasts}>{children}</ToastDispatchContext.Provider>
    </ToastContext.Provider>
  );
};
