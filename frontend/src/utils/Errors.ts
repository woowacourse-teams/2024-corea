import { githubAuthUrl } from "@/config/githubAuthUrl";

export const ERROR_STRATEGY = {
  TOAST: "toast",
  MODAL: "modal",
  REDIRECT: "redirect",
  ERROR_BOUNDARY: "errorBoundary",
  IGNORE: "ignore",
} as const;

export type ErrorHandlingStrategy = (typeof ERROR_STRATEGY)[keyof typeof ERROR_STRATEGY];

export type ModalStrategyOptions = {
  strategy: "modal";
  onConfirm: () => void;
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
  redirectTo?: string;

  constructor({ message, ...options }: CustomErrorOptions) {
    super(message);
    this.name = "CustomError";
    this.strategy = options.strategy ?? ERROR_STRATEGY.TOAST;

    if (options.strategy === ERROR_STRATEGY.MODAL) {
      this.onConfirm = options.onConfirm;
      this.onCancel = options.onCancel;
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
      strategy: "modal",
      onConfirm: () => {
        window.location.href = githubAuthUrl;
      },
      onCancel: () => {
        window.location.href = "/";
      },
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
