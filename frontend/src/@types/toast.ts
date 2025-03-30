export type ToastType = "error" | "success";

export interface Toast {
  message: string;
  type: ToastType;
  durationMs: number;
}
