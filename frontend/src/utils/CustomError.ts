import { githubAuthUrl } from "@/config/githubAuthUrl";
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
  strategy?: ErrorHandlingStrategy;
  meta?: CustomErrorMeta;
  status?: number;
}

export class CustomError extends Error {
  strategy: ErrorHandlingStrategy;
  meta?: CustomErrorMeta;
  status?: number;

  constructor({
    message,
    strategy = ERROR_STRATEGY.ERROR_BOUNDARY,
    meta,
    status,
  }: CustomErrorOptions) {
    super(message);
    this.name = "CustomError";
    this.strategy = strategy;
    this.meta = meta;
    this.status = status;
  }
}

export class ApiError extends CustomError {
  constructor(options: CustomErrorOptions) {
    super(options);
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
        cancelButtonText: "나중에 하기",
        onConfirm: () => {
          localStorage.clear();
          window.location.href = githubAuthUrl;
        },
        onCancel: () => {
          localStorage.clear();
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
