import { githubAuthUrl } from "@/config/githubAuthUrl";
import { ERROR_STRATEGY } from "@/constants/errorStrategy";

export type ErrorHandlingStrategy = (typeof ERROR_STRATEGY)[keyof typeof ERROR_STRATEGY];

export type ModalStrategyOptions = {
  strategy: "modal";
  onConfirm?: () => void;
  onCancel?: () => void;
  confirmButtonText?: string;
  cancelButtonText?: string;
};

export type RedirectStrategyOptions = {
  strategy: "redirect";
  redirectTo: string;
};

export type StrategyOptions =
  | ModalStrategyOptions
  | RedirectStrategyOptions
  | { strategy?: "toast" | "errorBoundary" | "ignore" };

export type CustomErrorOptions = {
  message: string;
} & StrategyOptions;

export class CustomError extends Error {
  strategy: ErrorHandlingStrategy;
  onConfirm?: () => void;
  onCancel?: () => void;
  confirmButtonText?: string;
  cancelButtonText?: string;
  redirectTo?: string;

  constructor({ message, ...options }: CustomErrorOptions) {
    super(message);
    this.name = "CustomError";
    this.strategy = options.strategy ?? ERROR_STRATEGY.TOAST;

    if (options.strategy === ERROR_STRATEGY.MODAL) {
      this.onConfirm = options.onConfirm;
      this.onCancel = options.onCancel;
      this.confirmButtonText = options.confirmButtonText;
      this.cancelButtonText = options.cancelButtonText;
    } else if (options.strategy === ERROR_STRATEGY.REDIRECT) {
      this.redirectTo = options.redirectTo;
    }
  }
}

export class ApiError extends CustomError {
  constructor(message: string) {
    super({ message, strategy: ERROR_STRATEGY.TOAST });
    this.name = "ApiError";
  }
}

export class AuthorizationError extends CustomError {
  constructor(message: string) {
    super({
      message,
      strategy: ERROR_STRATEGY.MODAL,
      onConfirm: () => {
        window.location.href = githubAuthUrl;
      },
      onCancel: () => {
        window.location.href = "/";
      },
      confirmButtonText: "로그인하기",
      cancelButtonText: "로그아웃하기",
    });
    this.name = "AuthorizationError";
  }
}

export class NetworkError extends CustomError {
  constructor(message: string) {
    super({ message, strategy: ERROR_STRATEGY.TOAST });
    this.name = "NetworkError";
  }
}

export class ValidationError extends CustomError {
  constructor(message: string) {
    super({ message, strategy: ERROR_STRATEGY.TOAST });
    this.name = "ValidationError";
  }
}
