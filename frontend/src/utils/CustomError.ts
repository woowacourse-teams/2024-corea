import { ERROR_STRATEGY } from "@/constants/errorStrategy";

export type ErrorHandlingStrategy = (typeof ERROR_STRATEGY)[keyof typeof ERROR_STRATEGY];

export type CustomErrorMeta = {
  confirmButtonText?: string;
  cancelButtonText?: string;
  onConfirm?: () => void;
  onCancel?: () => void;
  redirectTo?: string;
};

export interface CustomErrorOptions {
  message: string;
  strategy: ErrorHandlingStrategy;
  meta?: CustomErrorMeta;
}

export class CustomError extends Error {
  strategy: ErrorHandlingStrategy;
  meta?: CustomErrorMeta;

  constructor({ message, strategy = ERROR_STRATEGY.ERROR_BOUNDARY, meta }: CustomErrorOptions) {
    super(message);
    this.name = "CustomError";
    this.strategy = strategy;
    this.meta = meta;
  }
}

export class ApiError extends CustomError {
  constructor(message: string, strategy: ErrorHandlingStrategy, meta?: CustomErrorMeta) {
    super({ message, strategy, meta });
    this.name = "ApiError";
  }
}

export class AuthorizationError extends CustomError {
  constructor(message: string) {
    super({
      message,
      strategy: ERROR_STRATEGY.MODAL,
      meta: {
        confirmButtonText: "로그인하기",
        cancelButtonText: "로그아웃 유지",
        onConfirm: () => {
          window.location.href = "/github-auth";
        },
        onCancel: () => {
          window.location.href = "/";
        },
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
