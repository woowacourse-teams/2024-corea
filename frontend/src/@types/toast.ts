export type ToastType = "error" | "success";

export interface Toast {
  isOpen: boolean;
  message: string;
  type: ToastType;
}
