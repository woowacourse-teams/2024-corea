export class ApiError extends Error {
  constructor(message: string) {
    super(message);
    this.name = "ApiError";

    Object.setPrototypeOf(this, ApiError.prototype);
  }
}

export class AuthorizationError extends Error {
  constructor(message: string) {
    super(message);
  }
}

export class NetworkError extends Error {
  constructor(message: string) {
    super(message);
    this.name = "NetworkError";
  }
}

export class ValidationError extends Error {
  constructor(message: string) {
    super(message);
    this.name = "ValidationError";
  }
}
