export type ErrorHandlingStrategy = "toast" | "modal" | "redirect" | "errorBoundary" | "ignore";

export class CustomError extends Error {
  strategy: ErrorHandlingStrategy;

  constructor(message: string, strategy: ErrorHandlingStrategy = "toast") {
    super(message);
    this.name = "CustomError";
    this.strategy = strategy;
  }
}

export class ApiError extends CustomError {
  constructor(message: string, strategy: ErrorHandlingStrategy = "toast") {
    super(message, strategy);
    this.name = "ApiError";
  }
}

export class AuthorizationError extends CustomError {
  constructor(message: string, strategy: ErrorHandlingStrategy = "redirect") {
    super(message, strategy);
    this.name = "AuthorizationError";
  }
}

export class NetworkError extends CustomError {
  constructor(message: string, strategy: ErrorHandlingStrategy = "toast") {
    super(message, strategy);
    this.name = "NetworkError";
  }
}

export class ValidationError extends CustomError {
  constructor(message: string, strategy: ErrorHandlingStrategy = "toast") {
    super(message, strategy);
    this.name = "ValidationError";
  }
}
