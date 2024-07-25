import React, { createContext, useState } from "react";
import { Toast } from "@/@types/toast";

const ToastContext = createContext<Toast>({ isOpen: false, message: "" });
const ToastDispatchContext = createContext<React.Dispatch<React.SetStateAction<Toast>>>(() => {});

const ToastProvider = ({ children }: { children: React.ReactNode }) => {
  const [toastInfo, setToastInfo] = useState<Toast>({ isOpen: false, message: "" });

  return (
    <ToastContext.Provider value={toastInfo}>
      <ToastDispatchContext.Provider value={setToastInfo}>{children}</ToastDispatchContext.Provider>
    </ToastContext.Provider>
  );
};

export { ToastContext, ToastDispatchContext, ToastProvider };
