import React, { createContext, useState } from "react";
import { Toast } from "@/@types/toast";

const ToastContext = createContext<Toast>({ isOpen: false, message: "" });
const ToastDispatchContext = createContext<React.Dispatch<React.SetStateAction<Toast>>>(() => {});

const ToastProvider = ({ children }: { children: React.ReactNode }) => {
  const [modalInfo, setModalInfo] = useState<Toast>({ isOpen: false, message: "" });

  return (
    <ToastContext.Provider value={modalInfo}>
      <ToastDispatchContext.Provider value={setModalInfo}>{children}</ToastDispatchContext.Provider>
    </ToastContext.Provider>
  );
};

export { ToastContext, ToastDispatchContext, ToastProvider };
